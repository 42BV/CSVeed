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
