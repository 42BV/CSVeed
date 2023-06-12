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

/**
 * The Enum ParseState.
 */
public enum ParseState {

    /** The skip line. */
    SKIP_LINE(false, false, false, false, true),

    /** The skip line finished. */
    SKIP_LINE_FINISHED(false, true, false, false, true),

    /** The comment line. */
    COMMENT_LINE(false, false, false, false, true),

    /** The comment line finished. */
    COMMENT_LINE_FINISHED(false, true, false, false, true),

    /** The start of line. */
    START_OF_LINE(false, false, false, false, false),

    /** The outside before field. */
    OUTSIDE_BEFORE_FIELD(false, false, false, false, false),

    /** The outside after field. */
    OUTSIDE_AFTER_FIELD(false, false, false, false, false),

    /** The inside field. */
    INSIDE_FIELD(true, false, false, false, false),

    /** The first char inside quoted field. */
    FIRST_CHAR_INSIDE_QUOTED_FIELD(false, false, false, true, false),

    /** The inside quoted field. */
    INSIDE_QUOTED_FIELD(true, false, false, true, false),

    /** The escaping. */
    ESCAPING(false, false, false, false, false),

    /** The separator. */
    SEPARATOR(false, false, true, false, false),

    /** The line finished. */
    LINE_FINISHED(false, true, true, false, false),

    /** The finished. */
    FINISHED(false, true, true, false, false);

    /** When in this state, encountered symbols must be made part of the token. */
    private final boolean tokenize;

    /** When in this state, reading of the line is done. */
    private final boolean lineFinished;

    /** When in this state, the token may be popped. */
    private final boolean popToken;

    /** When a quote character is encountered here, this state MAY trigger an upgrade of the encountered symbol. */
    private final boolean upgradeQuoteToEscape;

    /** When in this state, ignore the entire line. */
    private final boolean ignore;

    /**
     * Instantiates a new parses the state.
     *
     * @param tokenize
     *            the tokenize
     * @param lineFinished
     *            the line finished
     * @param popToken
     *            the pop token
     * @param upgradeQuoteToEscape
     *            the upgrade quote to escape
     * @param ignore
     *            the ignore
     */
    ParseState(final boolean tokenize, final boolean lineFinished, final boolean popToken,
            final boolean upgradeQuoteToEscape, final boolean ignore) {
        this.tokenize = tokenize;
        this.lineFinished = lineFinished;
        this.popToken = popToken;
        this.upgradeQuoteToEscape = upgradeQuoteToEscape;
        this.ignore = ignore;
    }

    /**
     * Checks if is tokenize.
     *
     * @return true, if is tokenize
     */
    public boolean isTokenize() {
        return this.tokenize;
    }

    /**
     * Checks if is line finished.
     *
     * @return true, if is line finished
     */
    public boolean isLineFinished() {
        return this.lineFinished;
    }

    /**
     * Checks if is pop token.
     *
     * @return true, if is pop token
     */
    public boolean isPopToken() {
        return this.popToken;
    }

    /**
     * Checks if is upgrade quote to escape.
     *
     * @return true, if is upgrade quote to escape
     */
    public boolean isUpgradeQuoteToEscape() {
        return this.upgradeQuoteToEscape;
    }

    /**
     * Checks if is ignore.
     *
     * @return true, if is ignore
     */
    public boolean isIgnore() {
        return this.ignore;
    }

    /**
     * Trim.
     *
     * @return true, if successful
     */
    public boolean trim() {
        return this == INSIDE_FIELD;
    }
}
