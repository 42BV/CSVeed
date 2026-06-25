/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.common;

import java.util.Locale;

/**
 * The Class ColumnExcel.
 */
public class ColumnExcel extends Column {

    /**
     * Instantiates a new column excel.
     *
     * @param columnExcel
     *            the column excel
     */
    public ColumnExcel(String columnExcel) {
        super(excelColumnToColumnIndex(columnExcel));
    }

    /**
     * Excel column to column index.
     *
     * @param excelColumn
     *            the excel column
     *
     * @return the int
     */
    private static int excelColumnToColumnIndex(String excelColumn) {
        excelColumn = excelColumn.toUpperCase(Locale.getDefault());
        int sum = 0;
        for (int i = 0; i < excelColumn.length(); i++) {
            char currentChar = excelColumn.charAt(i);
            sum *= 26;
            sum += currentChar - 'A' + 1;
        }
        return sum;
    }

}
