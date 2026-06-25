/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.csveed.token.ParseState;
import org.junit.jupiter.api.Test;

/**
 * The Class EnumConverterTest.
 */
class EnumConverterTest {

    /**
     * Convert to enum.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void convertToEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<>(ParseState.class);
        assertEquals(ParseState.SKIP_LINE, converter.fromString("SKIP_LINE"));
    }

    /**
     * Convert from enum.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void convertFromEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<>(ParseState.class);
        assertEquals("SKIP_LINE", converter.toString(ParseState.SKIP_LINE));
    }

}
