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

import org.junit.jupiter.api.Test;

public class EasyAbstractConverterTest {

    @Test
    public void testEasyAbstractConverter() throws Exception {
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
