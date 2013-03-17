package org.csveed.common;

public abstract class ColumnKey implements Comparable<ColumnKey> {

    public abstract Integer getPriority();

    public boolean sameKeyType(ColumnKey columnKey) {
        return getPriority().equals(columnKey.getPriority());
    }

    public int keyTypeCompare(ColumnKey columnKey) {
        return getPriority().compareTo(columnKey.getPriority());
    }

}
