/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.report.CsvException;
import org.csveed.test.model.BeanSimple;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanPropertiesTest.
 */
class BeanPropertiesTest {

    /**
     * Map at column index 0.
     */
    @Test
    void mapAtColumnIndex0() {
        BeanProperties properties = new BeanProperties(BeanSimple.class);
        assertThrows(CsvException.class, () -> properties.mapIndexToProperty(0, "name"));
    }
}
