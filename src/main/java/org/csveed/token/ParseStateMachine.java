/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.token;

import static org.csveed.token.ParseState.COMMENT_LINE;
import static org.csveed.token.ParseState.COMMENT_LINE_FINISHED;
import static org.csveed.token.ParseState.ESCAPING;
import static org.csveed.token.ParseState.FINISHED;
import static org.csveed.token.ParseState.FIRST_CHAR_INSIDE_QUOTED_FIELD;
import static org.csveed.token.ParseState.INSIDE_FIELD;
import static org.csveed.token.ParseState.INSIDE_QUOTED_FIELD;
import static org.csveed.token.ParseState.LINE_FINISHED;
import static org.csveed.token.ParseState.OUTSIDE_AFTER_FIELD;
import static org.csveed.token.ParseState.OUTSIDE_BEFORE_FIELD;
import static org.csveed.token.ParseState.SEPARATOR;
import static org.csveed.token.ParseState.SKIP_LINE;
import static org.csveed.token.ParseState.SKIP_LINE_FINISHED;
import static org.csveed.token.ParseState.START_OF_LINE;

import org.csveed.common.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Yep, a state machine. Managing all kinds of booleans to form a pseudo-state doesn't work really well whereas a state
 * machine does. The state machine takes one character at a time, checks routes to the new state if necessary and holds
 * tokens, which it returns whenever a field-end ('popToken') has been found.
 */
public class ParseStateMachine {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ParseStateMachine.class);

    /** The state. */
    private ParseState state = START_OF_LINE;

    /** The token. */
    private StringBuilder token = new StringBuilder();

    /** The characters read. */
    private int charactersRead;

    /** The symbol mapping. */
    private SymbolMapping symbolMapping = new SymbolMapping();

    /** The token state. */
    private TokenState tokenState = TokenState.RESET;

    /** The trim. */
    private boolean trim = true;

    /** The trash. */
    private boolean trash;

    /** The current column. */
    private Column currentColumn = new Column();

    /** The current line. */
    private int currentLine = 1;

    /** The new line. */
    private int newLine = currentLine;

    /**
     * Gets the current line.
     *
     * @return the current line
     */
    public int getCurrentLine() {
        return this.currentLine;
    }

    /**
     * Gets the current column.
     *
     * @return the current column
     */
    public int getCurrentColumn() {
        return this.currentColumn.getColumnIndex();
    }

    /**
     * Checks if is trash.
     *
     * @return true, if is trash
     */
    public boolean isTrash() {
        return this.trash;
    }

    /**
     * Offer symbol.
     *
     * @param symbolCharacter
     *            the symbol character
     *
     * @return the string
     *
     * @throws ParseException
     *             the parse exception
     */
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

    /**
     * Checks if is token start.
     *
     * @return true, if is token start
     */
    public boolean isTokenStart() {
        return tokenState.isStart();
    }

    /**
     * Checks if is line finished.
     *
     * @return true, if is line finished
     */
    public boolean isLineFinished() {
        return state.isLineFinished();
    }

    /**
     * Checks if is finished.
     *
     * @return true, if is finished
     */
    public boolean isFinished() {
        return state == FINISHED;
    }

    /**
     * Ignore line.
     *
     * @return true, if successful
     */
    public boolean ignoreLine() {
        return state.isIgnore() || isEmptyLine();
    }

    /**
     * Checks if is empty line.
     *
     * @return true, if is empty line
     */
    public boolean isEmptyLine() {
        return charactersRead == 0;
    }

    /**
     * Determine state.
     *
     * @param symbolCharacter
     *            the symbol character
     * @param symbol
     *            the symbol
     *
     * @return the parses the state
     *
     * @throws ParseException
     *             the parse exception
     */
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
                if (EncounteredSymbol.COMMENT_SYMBOL.equals(symbol) && symbolMapping.isSkipCommentLines()) {
                    return COMMENT_LINE;
                }
                //$FALL-THROUGH$
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

    /**
     * Sets the symbol mapping.
     *
     * @param symbolMapping
     *            the new symbol mapping
     */
    public void setSymbolMapping(SymbolMapping symbolMapping) {
        this.symbolMapping = symbolMapping;
    }

    /**
     * Gets the symbol mapping.
     *
     * @return the symbol mapping
     */
    public SymbolMapping getSymbolMapping() {
        return this.symbolMapping;
    }

}
