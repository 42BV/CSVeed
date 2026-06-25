/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The Class EasyAbstractConverterTest.
 */
class EasyAbstractConverterTest {

    /**
     * Test easy abstract converter.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testEasyAbstractConverter() throws Exception {
        Converter<Coordinate> converter = new EasyAbstractConverter<Coordinate>(Coordinate.class) {
            @Override
            public Coordinate fromString(String text) throws Exception {
                return Coordinate.fromString(text);
            }
        };
        Coordinate coords = converter.fromString("11/38");
        assertEquals((Integer) 11, coords.getX());
        assertEquals((Integer) 38, coords.getY());
    }
}
