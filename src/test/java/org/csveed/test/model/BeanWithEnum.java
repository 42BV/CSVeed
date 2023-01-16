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

public class BeanWithEnum {

    private ParseState parseState;

    public ParseState getParseState() {
        return parseState;
    }

    public void setParseState(ParseState parseState) {
        this.parseState = parseState;
    }
}
