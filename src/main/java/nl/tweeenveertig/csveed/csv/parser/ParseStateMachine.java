package nl.tweeenveertig.csveed.csv.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nl.tweeenveertig.csveed.csv.parser.ParseState.*;
import static nl.tweeenveertig.csveed.csv.parser.EncounteredSymbol.*;

/**
* Yep, a state machine. Managing all kinds of booleans to form a pseudo-state doesn't work really well
* whereas a state machine does. The state machine takes one character at a time, checks routes to the new
* state if necessary and holds tokens, which it returns whenever a field-end ('popToken') has been found.
* @author Robert Bor
*/
public class ParseStateMachine {

    public static final Logger LOG = LoggerFactory.getLogger(ParseStateMachine.class);

    private ParseState state = START_OF_LINE;

    private StringBuilder token = new StringBuilder();

    private int charactersRead = 0;

    private SymbolMapping symbolMapping = new SymbolMapping();

    private TokenState tokenState = TokenState.RESET;

    public boolean isEol(int symbolCharacter) {
        return symbolMapping.find(symbolCharacter, OUTSIDE_FIELD) == EOL_SYMBOL;
    }

    public String offerSymbol(int symbolCharacter) throws ParseException {
        if (tokenState.isStart()) {
            tokenState = tokenState.next();
        }

        EncounteredSymbol symbol = symbolMapping.find(symbolCharacter, state);
        ParseState newState = determineState(symbolCharacter, symbol);
        LOG.debug((char)symbolCharacter+" ("+symbol+"): "+state+" => "+newState);

        if (newState.isTokenize()) {
            if (tokenState.isReset()) {
                tokenState = tokenState.next();
            }
            token.append((char)symbolCharacter);
        }
        String returnToken = null;

        if (newState.isPopToken()) {
            returnToken = token.toString();
            token = new StringBuilder();
            tokenState = tokenState.next();
        }

        if (!newState.isLineFinished()) {
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

    public boolean isEmptyLine() {
        return charactersRead == 0;
    }

    public void newLine() {
        if (state != FINISHED) {
            state = START_OF_LINE;
        }
        charactersRead = 0;
    }

    protected ParseState determineState(int symbolCharacter, EncounteredSymbol symbol) throws ParseException {

        switch (state) {
            case START_OF_LINE:
            case SEPARATOR:
                switch(symbol) {
                    case QUOTE_SYMBOL:
                        return FIRST_CHAR_INSIDE_QUOTED_FIELD;
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL :
                        return LINE_FINISHED;
                    default :
                        return INSIDE_FIELD;
                }
            case OUTSIDE_FIELD:
                switch(symbol) {
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL :
                        return LINE_FINISHED;
                    default :
                        throw new ParseException(state, symbolCharacter, symbol);
                }
            case INSIDE_FIELD:
                switch (symbol) {
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    case END_OF_FILE_SYMBOL:
                        return FINISHED;
                    case EOL_SYMBOL :
                        return LINE_FINISHED;
                    case QUOTE_SYMBOL :
                        throw new ParseException(state, symbolCharacter, symbol);
                    default :
                        return INSIDE_FIELD;
                }
            case FIRST_CHAR_INSIDE_QUOTED_FIELD:
            case INSIDE_QUOTED_FIELD:
                switch (symbol) {
                    case QUOTE_SYMBOL :
                        return OUTSIDE_FIELD;
                    case ESCAPE_SYMBOL :
                        return ESCAPING;
                    case END_OF_FILE_SYMBOL:
                        throw new ParseException(state, symbolCharacter, symbol);
                    default :
                        return INSIDE_QUOTED_FIELD;
                }
            case ESCAPING:
                if (symbolMapping.isSameCharactersForEscapeAndQuote()) { // This is the default
                    switch (symbol) {
                        case QUOTE_SYMBOL :
                            return INSIDE_QUOTED_FIELD;
                        case EOL_SYMBOL: // Needed when quote/escape are the same: ...abc"\n
                            return LINE_FINISHED;
                        case SEPARATOR_SYMBOL : // Needed when quote/escape are the same: ...abc";
                            return SEPARATOR;
                        case END_OF_FILE_SYMBOL:
                            return FINISHED;
                        default :
                            throw new ParseException(state, symbolCharacter, symbol);
                    }
                } else { // We're lenient -- accept everything
                    return INSIDE_QUOTED_FIELD;
                }
            default :
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
