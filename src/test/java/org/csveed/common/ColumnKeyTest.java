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
package org.csveed.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The Class ColumnKeyTest.
 */
class ColumnKeyTest {

    /**
     * Column name key equals.
     */
    @Test
    void columnNameKeyEquals() {
        ColumnNameKey key1 = new ColumnNameKey("alpha");
        ColumnNameKey key2 = new ColumnNameKey("alpha");
        assertEquals(key1, key2);
    }

    /**
     * Key 1 less than key 2.
     */
    @Test
    void key1LessThanKey2() {
        ColumnNameKey key1 = new ColumnNameKey("alpha");
        ColumnNameKey key2 = new ColumnNameKey("beta");
        assertEquals(-1, key1.compareTo(key2));
    }

}
