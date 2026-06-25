/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.model;

import org.csveed.token.ParseState;

/**
 * The Class BeanWithEnum.
 */
public class BeanWithEnum {

    /** The parse state. */
    private ParseState parseState;

    /**
     * Gets the parses the state.
     *
     * @return the parses the state
     */
    public ParseState getParseState() {
        return parseState;
    }

    /**
     * Sets the parses the state.
     *
     * @param parseState
     *            the new parses the state
     */
    public void setParseState(ParseState parseState) {
        this.parseState = parseState;
    }
}
