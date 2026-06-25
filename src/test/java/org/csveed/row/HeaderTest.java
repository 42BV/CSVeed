/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.row;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.junit.jupiter.api.Test;

/**
 * The Class HeaderTest.
 */
class HeaderTest {

    /**
     * Gets the non existing column name.
     */
    @Test
    void getNonExistingColumnName() {
        Header header = new HeaderImpl(createLine("alpha"));
        assertThrows(CsvException.class, () -> header.getIndex("does-not-exist"));
    }

    /**
     * Gets the non existing column index.
     */
    @Test
    void getNonExistingColumnIndex() {
        Header header = new HeaderImpl(createLine("alpha"));
        assertThrows(CsvException.class, () -> header.getName(13));
    }

    /**
     * To lower case.
     */
    @Test
    void toLowerCase() {
        Header header = new HeaderImpl(createLine("Alpha"));
        assertEquals("Alpha", header.getName(1));
        assertEquals(1, header.getIndex("Alpha"));
    }

    /**
     * Creates the line.
     *
     * @param cell
     *            the cell
     *
     * @return the line with info
     */
    protected LineWithInfo createLine(String cell) {
        LineWithInfo line = new LineWithInfo();
        line.addCell(cell);
        return line;
    }
}
