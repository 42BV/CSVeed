package org.csveed.common;

public class ColumnName extends Column {

    private String columnName;

    public ColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getExcelColumn() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getColumnIndex() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Column nextColumn() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Column nextLine() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColumnName)) {
            return false;
        }
        ColumnName column = (ColumnName)obj;
        return this.columnName.equals(column.columnName);
    }

    @Override
    public int hashCode() {
        return getColumnIndex() % 10;
    }

}
