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
