/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
