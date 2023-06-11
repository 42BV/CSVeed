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

public enum ParseState {
    SKIP_LINE(false, false, false, false, true), SKIP_LINE_FINISHED(false, true, false, false, true),
    COMMENT_LINE(false, false, false, false, true), COMMENT_LINE_FINISHED(false, true, false, false, true),
    START_OF_LINE(false, false, false, false, false), OUTSIDE_BEFORE_FIELD(false, false, false, false, false),
    OUTSIDE_AFTER_FIELD(false, false, false, false, false), INSIDE_FIELD(true, false, false, false, false),
    FIRST_CHAR_INSIDE_QUOTED_FIELD(false, false, false, true, false),
    INSIDE_QUOTED_FIELD(true, false, false, true, false), ESCAPING(false, false, false, false, false),
    SEPARATOR(false, false, true, false, false), LINE_FINISHED(false, true, true, false, false),
    FINISHED(false, true, true, false, false);

    /**
     * When in this state, encountered symbols must be made part of the token
     */
    private final boolean tokenize;

    /**
     * When in this state, reading of the line is done
     */
    private final boolean lineFinished;

    /**
     * When in this state, the token may be popped
     */
    private final boolean popToken;

    /**
     * When a quote character is encountered here, this state MAY trigger an upgrade of the encountered symbol
     */
    private final boolean upgradeQuoteToEscape;

    /**
     * When in this state, ignore the entire line
     */
    private final boolean ignore;

    ParseState(final boolean tokenize, final boolean lineFinished, final boolean popToken,
            final boolean upgradeQuoteToEscape, final boolean ignore) {
        this.tokenize = tokenize;
        this.lineFinished = lineFinished;
        this.popToken = popToken;
        this.upgradeQuoteToEscape = upgradeQuoteToEscape;
        this.ignore = ignore;
    }

    public boolean isTokenize() {
        return this.tokenize;
    }

    public boolean isLineFinished() {
        return this.lineFinished;
    }

    public boolean isPopToken() {
        return this.popToken;
    }

    public boolean isUpgradeQuoteToEscape() {
        return this.upgradeQuoteToEscape;
    }

    public boolean isIgnore() {
        return this.ignore;
    }

    public boolean trim() {
        return this == INSIDE_FIELD;
    }
}
