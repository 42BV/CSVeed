package org.csveed.common;

public class ColumnNameKey extends ColumnKey<String> {

    private final String columnName;

    public ColumnNameKey(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public int compareTo(String columnName) {
        return this.columnName.compareTo(columnName);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColumnNameKey)) {
            return false;
        }
        return columnName.equals(((ColumnNameKey)obj).columnName);
    }

    @Override
    public int hashCode() {
        return columnName.hashCode();
    }

    @Override
    public String toString() {
        return "Column Name: "+columnName;
    }

}
