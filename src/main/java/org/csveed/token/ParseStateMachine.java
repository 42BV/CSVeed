package org.csveed.token;

import static org.csveed.token.ParseState.*;

import org.csveed.common.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Yep, a state machine. Managing all kinds of booleans to form a pseudo-state doesn't work really well whereas a state
 * machine does. The state machine takes one character at a time, checks routes to the new state if necessary and holds
 * tokens, which it returns whenever a field-end ('popToken') has been found.
 *
 * @author Robert Bor
 */
public class ParseStateMachine {

    private static final Logger LOG = LoggerFactory.getLogger(ParseStateMachine.class);

    private ParseState state = START_OF_LINE;

    private StringBuilder token = new StringBuilder();

    private int charactersRead;

    private SymbolMapping symbolMapping = new SymbolMapping();

    private TokenState tokenState = TokenState.RESET;

    private boolean trim = true;

    private boolean trash;

    private Column currentColumn = new Column();

    private int currentLine = 1;

    private int newLine = currentLine;

    public int getCurrentLine() {
        return this.currentLine;
    }

    public int getCurrentColumn() {
        return this.currentColumn.getColumnIndex();
    }

    public boolean isTrash() {
        return this.trash;
    }

    public String offerSymbol(int symbolCharacter) throws ParseException {

        this.trash = false;

        EncounteredSymbol symbol = symbolMapping.find(symbolCharacter, state);

        if (symbol.isTrash()) {
            this.trash = true;
            return null;
        }

        if (isFinished()) {
            throw new ParseException(state, symbolCharacter, symbol);
        }

        if (currentLine != newLine) {
            state = START_OF_LINE;
            charactersRead = 0;
            currentColumn = currentColumn.nextLine();
            currentLine = newLine;
        }

        if (currentLine < symbolMapping.getStartLine()) {
            state = SKIP_LINE;
        }

        if (tokenState.isStart()) {
            tokenState = tokenState.next();
        }

        ParseState newState = determineState(symbolCharacter, symbol);
        LOG.debug("{} ({}): {} => {}", (char) symbolCharacter, symbol, state, newState);

        if (newState.isTokenize()) {
            if (tokenState.isReset()) {
                trim = newState.trim();
                tokenState = tokenState.next();
            }
            token.append((char) symbolCharacter);
        }
        String returnToken = null;

        if (newState.isPopToken()) {
            returnToken = token.toString();
            if (trim) {
                returnToken = returnToken.trim();
            }
            token = new StringBuilder();
            tokenState = tokenState.next();
            currentColumn = currentColumn.nextColumn();
        }

        if (newState.isLineFinished()) {
            newLine++;
        } else {
            charactersRead++;
        }

        state = newState;

        return returnToken;
    }

    public boolean isTokenStart() {
        return tokenState.isStart();
    }

    public boolean isLineFinished() {
        return state.isLineFinished();
    }

    public boolean isFinished() {
        return state == FINISHED;
    }

    public boolean ignoreLine() {
        return state.isIgnore() || isEmptyLine();
    }

    public boolean isEmptyLine() {
        return charactersRead == 0;
    }

    protected ParseState determineState(int symbolCharacter, EncounteredSymbol symbol) throws ParseException {

        switch (state) {
            case SKIP_LINE:
                switch (symbol) {
                    case EOL_SYMBOL:
                        return SKIP_LINE_FINISHED;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    default:
                        return SKIP_LINE;
                }
            case COMMENT_LINE:
                switch (symbol) {
                    case EOL_SYMBOL:
                        return COMMENT_LINE_FINISHED;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    default:
                        return COMMENT_LINE;
                }
            case START_OF_LINE:
                switch (symbol) {
                    case COMMENT_SYMBOL:
                        if (symbolMapping.isSkipCommentLines()) {
                            return COMMENT_LINE;
                        }
                } // Fallthrough intentional
            case SEPARATOR:
                switch (symbol) {
                    case SPACE_SYMBOL:
                        return OUTSIDE_BEFORE_FIELD;
                    case QUOTE_SYMBOL:
                        return FIRST_CHAR_INSIDE_QUOTED_FIELD;
                    case SEPARATOR_SYMBOL:
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL:
                        return LINE_FINISHED;
                    default:
                        return INSIDE_FIELD;
                }
            case OUTSIDE_BEFORE_FIELD:
                switch (symbol) {
                    case SPACE_SYMBOL:
                        return OUTSIDE_BEFORE_FIELD;
                    case SEPARATOR_SYMBOL:
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL:
                        return LINE_FINISHED;
                    case QUOTE_SYMBOL:
                        return FIRST_CHAR_INSIDE_QUOTED_FIELD;
                    default:
                        return INSIDE_FIELD;
                }
            case OUTSIDE_AFTER_FIELD:
                switch (symbol) {
                    case SPACE_SYMBOL:
                        return OUTSIDE_AFTER_FIELD;
                    case SEPARATOR_SYMBOL:
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL:
                        return LINE_FINISHED;
                    default:
                        throw new ParseException(state, symbolCharacter, symbol);
                }
            case INSIDE_FIELD:
                switch (symbol) {
                    case SEPARATOR_SYMBOL:
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL:
                        return LINE_FINISHED;
                    case QUOTE_SYMBOL:
                        throw new ParseException(state, symbolCharacter, symbol);
                    default:
                        return INSIDE_FIELD;
                }
            case FIRST_CHAR_INSIDE_QUOTED_FIELD:
            case INSIDE_QUOTED_FIELD:
                switch (symbol) {
                    case QUOTE_SYMBOL:
                        return OUTSIDE_AFTER_FIELD;
                    case ESCAPE_SYMBOL:
                        return ESCAPING;
                    case END_OF_FILE_SYMBOL:
                        throw new ParseException(state, symbolCharacter, symbol);
                    default:
                        return INSIDE_QUOTED_FIELD;
                }
            case ESCAPING:
                if (symbolMapping.isSameCharactersForEscapeAndQuote()) { // This is the default
                    switch (symbol) {
                        case SPACE_SYMBOL:
                            return OUTSIDE_AFTER_FIELD;
                        case QUOTE_SYMBOL:
                            return INSIDE_QUOTED_FIELD;
                        case EOL_SYMBOL: // Needed when quote/escape are the same: ...abc"\n
                            return LINE_FINISHED;
                        case SEPARATOR_SYMBOL: // Needed when quote/escape are the same: ...abc";
                            return SEPARATOR;
                        case END_OF_FILE_SYMBOL:
                            return FINISHED;
                        default:
                            throw new ParseException(state, symbolCharacter, symbol);
                    }
                }
                // We're lenient -- accept everything
                return INSIDE_QUOTED_FIELD;
            default:
                throw new ParseException(state, symbolCharacter, symbol);
        }
    }

    public void setSymbolMapping(SymbolMapping symbolMapping) {
        this.symbolMapping = symbolMapping;
    }

    public SymbolMapping getSymbolMapping() {
        return this.symbolMapping;
    }

}
