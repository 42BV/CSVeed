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
