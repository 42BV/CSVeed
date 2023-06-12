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

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

/**
 * The Class Column.
 */
public class Column implements Comparable<Column> {

    /** The Constant FIRST_COLUMN_INDEX. */
    public static final int FIRST_COLUMN_INDEX = 1;

    /** The column index. */
    private int columnIndex = -1;

    /** The column name. */
    private String columnName;

    /** The header. */
    private Header header;

    /** The key. */
    private ColumnKey key;

    /**
     * Instantiates a new column.
     *
     * @param columnName
     *            the column name
     */
    public Column(String columnName) {
        setColumnName(columnName);
    }

    /**
     * Instantiates a new column.
     */
    public Column() {
        this(FIRST_COLUMN_INDEX);
    }

    /**
     * Instantiates a new column.
     *
     * @param column
     *            the column
     */
    public Column(Column column) {
        setColumnIndex(column.getColumnIndex());
    }

    /**
     * Instantiates a new column.
     *
     * @param columnIndex
     *            the column index
     */
    public Column(int columnIndex) {
        if (columnIndex <= 0) {
            throw new CsvException(
                    new GeneralError("Column index cannot be set at 0 or lower. Column indexes are 1-based"));
        }
        setColumnIndex(columnIndex);
    }

    /**
     * Column index to excel column.
     *
     * @param columnIndex
     *            the column index
     *
     * @return the string
     */
    private String columnIndexToExcelColumn(int columnIndex) {
        StringBuilder excelColumn = new StringBuilder();
        while (columnIndex % 26 > 0) {
            excelColumn.insert(0, (char) (columnIndex % 26 + 'A' - 1));
            columnIndex /= 26;
        }
        return excelColumn.toString();
    }

    /**
     * Sets the header.
     *
     * @param header
     *            the header
     *
     * @return the column
     */
    public Column setHeader(Header header) {
        this.header = header;
        if (this.header != null) {
            setColumnName(header.getName(this.columnIndex));
        }
        return this;
    }

    /**
     * Sets the column index.
     *
     * @param columnIndex
     *            the new column index
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
        setKey(new ColumnIndexKey(this.columnIndex));
    }

    /**
     * Sets the column name.
     *
     * @param columnName
     *            the new column name
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName.toLowerCase(Locale.getDefault());
        setKey(new ColumnNameKey(this.columnName));
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the new key
     */
    public void setKey(ColumnKey key) {
        this.key = key;
    }

    /**
     * Gets the excel column.
     *
     * @return the excel column
     */
    public String getExcelColumn() {
        return columnIndexToExcelColumn(this.columnIndex);
    }

    /**
     * Gets the column index.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return this.columnIndex;
    }

    /**
     * Gets the column name.
     *
     * @return the column name
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * Next column.
     *
     * @return the column
     */
    public Column nextColumn() {
        return new Column(this.getColumnIndex() + 1).setHeader(header);
    }

    /**
     * Next line.
     *
     * @return the column
     */
    public Column nextLine() {
        return new Column().setHeader(header);
    }

    /**
     * Gets the column text.
     *
     * @return the column text
     */
    public String getColumnText() {
        return (columnIndex == -1 ? "" : " index " + columnIndex + " (" + getExcelColumn() + ")")
                + (columnName == null ? "" : " name \"" + columnName + "\"");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Column)) {
            return false;
        }
        return this.key.equals(((Column) obj).key);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return this.key.toString();
    }

    @Override
    public int compareTo(Column column) {
        return this.key.compareTo(column.key);
    }
}
