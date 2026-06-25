/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.token;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The Class TokenStateTest.
 */
class TokenStateTest {

    /**
     * Next state.
     */
    @Test
    void nextState() {
        assertEquals(TokenState.RESET, TokenState.PROCESSING.next());
        assertEquals(TokenState.START, TokenState.RESET.next());
        assertEquals(TokenState.PROCESSING, TokenState.START.next());
    }
}
