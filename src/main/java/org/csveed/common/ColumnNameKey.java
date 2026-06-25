/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.common;

/**
 * The Class ColumnNameKey.
 */
public class ColumnNameKey extends ColumnKey {

    /** The column name. */
    private final String columnName;

    /**
     * Instantiates a new column name key.
     *
     * @param columnName
     *            the column name
     */
    public ColumnNameKey(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int compareTo(ColumnKey columnKey) {
        if (!sameKeyType(columnKey)) {
            return keyTypeCompare(columnKey);
        }
        return this.columnName.compareTo(((ColumnNameKey) columnKey).columnName);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColumnNameKey)) {
            return false;
        }
        return compareTo((ColumnNameKey) obj) == 0;
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    @Override
    public String toString() {
        return "Column Name: " + columnName;
    }

    @Override
    public Integer getPriority() {
        return 2;
    }
}
