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

public class ColumnNameKey extends ColumnKey {

    private final String columnName;

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
