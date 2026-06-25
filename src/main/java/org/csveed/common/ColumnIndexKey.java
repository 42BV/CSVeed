/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.common;

/**
 * The Class ColumnIndexKey.
 */
public class ColumnIndexKey extends ColumnKey {

    /** The column index. */
    private final Integer columnIndex;

    /**
     * Instantiates a new column index key.
     *
     * @param columnIndex
     *            the column index
     */
    public ColumnIndexKey(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public int compareTo(ColumnKey columnKey) {
        if (!sameKeyType(columnKey)) {
            return keyTypeCompare(columnKey);
        }
        return this.columnIndex.compareTo(((ColumnIndexKey) columnKey).columnIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColumnIndexKey)) {
            return false;
        }
        return compareTo((ColumnIndexKey) obj) == 0;
    }

    @Override
    public int hashCode() {
        return columnIndex.hashCode();
    }

    @Override
    public String toString() {
        return "Column Index: " + columnIndex;
    }

    @Override
    public Integer getPriority() {
        return 1;
    }
}
