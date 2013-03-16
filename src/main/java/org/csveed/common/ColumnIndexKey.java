package org.csveed.common;

public class ColumnIndexKey extends ColumnKey<Integer> {

    private final Integer columnIndex;

    public ColumnIndexKey(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public int compareTo(Integer columnIndex) {
        return this.columnIndex.compareTo(columnIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColumnIndexKey)) {
            return false;
        }
        return columnIndex.equals(((ColumnIndexKey)obj).columnIndex);
    }

    @Override
    public int hashCode() {
        return columnIndex.hashCode();
    }

    @Override
    public String toString() {
        return "Column Index: "+columnIndex;
    }

}
