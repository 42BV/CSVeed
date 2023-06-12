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
 * The Enum TokenState.
 */
public enum TokenState {

    /** The reset. */
    RESET,

    /** The start. */
    START,

    /** The processing. */
    PROCESSING;

    /**
     * Next.
     *
     * @return the token state
     */
    public TokenState next() {
        return values()[(ordinal() + 1) % 3];
    }

    /**
     * Checks if is start.
     *
     * @return true, if is start
     */
    public boolean isStart() {
        return this == START;
    }

    /**
     * Checks if is reset.
     *
     * @return true, if is reset
     */
    public boolean isReset() {
        return this == RESET;
    }
}
