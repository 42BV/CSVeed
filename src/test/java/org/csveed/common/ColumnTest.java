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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.TreeMap;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.row.HeaderImpl;
import org.csveed.row.LineWithInfo;
import org.junit.jupiter.api.Test;

/**
 * The Class ColumnTest.
 */
class ColumnTest {

    /**
     * Excel column to column index.
     */
    @Test
    void excelColumnToColumnIndex() {
        Column excel = new ColumnExcel("AH");
        assertEquals(34, excel.getColumnIndex());
    }

    /**
     * Largest possible index.
     */
    @Test
    void largestPossibleIndex() {
        Column excel = new ColumnExcel("ZZ");
        assertEquals(702, excel.getColumnIndex());
    }

    /**
     * Column index to excel column.
     */
    @Test
    void columnIndexToExcelColumn() {
        Column excel = new Column(34);
        assertEquals("AH", excel.getExcelColumn());
    }

    /**
     * Wrong index.
     */
    @Test
    void wrongIndex() {
        assertThrows(CsvException.class, () -> {
            new Column(0);
        });
    }

    /**
     * Next column.
     */
    @Test
    void nextColumn() {
        Column column = new Column(3);
        assertEquals(4, column.nextColumn().getColumnIndex());
    }

    /**
     * Reset.
     */
    @Test
    void reset() {
        Column column = new Column(3);
        assertEquals(Column.FIRST_COLUMN_INDEX, column.nextLine().getColumnIndex());
    }

    /**
     * Equals.
     */
    @Test
    void equals() {
        assertEquals(new Column(3), new Column(3));
    }

    /**
     * Tree map.
     */
    @Test
    void treeMap() {
        Map<Column, String> map = new TreeMap<>();
        Column storeColumn = new Column("name");
        map.put(storeColumn, "alpha");
        LineWithInfo line = new LineWithInfo();
        line.addCell("name");
        Header header = new HeaderImpl(line);
        Column searchColumn = new Column().setHeader(header);
        assertNotNull(map.get(searchColumn));
    }

    /**
     * Tree map with column index.
     */
    @Test
    void treeMapWithColumnIndex() {
        Map<Column, String> map = new TreeMap<>();
        map.put(new Column(1), "alpha");
        map.put(new Column(2), "beta");
        map.put(new Column(3), "gamma");
        assertEquals("alpha", map.get(new Column(1)));
        assertEquals("beta", map.get(new Column(2)));
        assertEquals("gamma", map.get(new Column(3)));
    }

}
