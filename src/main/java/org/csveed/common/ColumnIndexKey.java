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
