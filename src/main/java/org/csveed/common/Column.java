package org.csveed.common;

public abstract class Column {

    public abstract String getExcelColumn();

    public abstract int getColumnIndex();

    public abstract Column nextColumn();

    public abstract Column nextLine();

}
