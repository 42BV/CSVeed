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
