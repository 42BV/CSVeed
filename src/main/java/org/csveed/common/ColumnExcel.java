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
