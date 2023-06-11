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
