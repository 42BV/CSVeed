/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.token;

/**
 * The Class ParseException.
 */
public class ParseException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The state. */
    private final ParseState state;

    /** The symbol. */
    private final EncounteredSymbol symbol;

    /** The symbol character. */
    private final int symbolCharacter;

    /**
     * Instantiates a new parses the exception.
     *
     * @param state
     *            the state
     * @param symbolCharacter
     *            the symbol character
     * @param symbol
     *            the symbol
     */
    public ParseException(ParseState state, int symbolCharacter, EncounteredSymbol symbol) {
        this.state = state;
        this.symbolCharacter = symbolCharacter;
        this.symbol = symbol;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public ParseState getState() {
        return state;
    }

    /**
     * Gets the symbol.
     *
     * @return the symbol
     */
    public EncounteredSymbol getSymbol() {
        return symbol;
    }

    /**
     * Gets the symbol character.
     *
     * @return the symbol character
     */
    public int getSymbolCharacter() {
        return symbolCharacter;
    }

    @Override
    public String getMessage() {
        return "Illegal state transition: Parsing symbol " + getSymbol() + " [" + getSymbolCharacter() + "] in state "
                + getState();
    }

}
