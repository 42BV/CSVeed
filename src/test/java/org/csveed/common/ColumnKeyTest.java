/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
