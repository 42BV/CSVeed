package org.csveed.common;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

public class Column {

    public static final int FIRST_COLUMN_INDEX = 1;

    private int columnIndex = -1;

    private String columnName = null;

    private Header header;

    public Column(String columnName) {
        this.columnName = columnName.toLowerCase();
    }

    public Column() {
        this(FIRST_COLUMN_INDEX);
    }

    public Column(int columnIndex) {
        if (columnIndex <= 0) {
            throw new CsvException(new GeneralError("Column index cannot be set at 0 or lower. Column indexes are 1-based"));
        }
        this.columnIndex = columnIndex;
    }

    private String columnIndexToExcelColumn(int columnIndex) {
        StringBuilder excelColumn = new StringBuilder();
        while (columnIndex % 26 > 0) {
            excelColumn.insert(0, (char) (columnIndex % 26 + 'A' - 1));
            columnIndex /= 26;
        }
        return excelColumn.toString();
    }

    public String getExcelColumn() {
        return columnIndexToExcelColumn(this.columnIndex);
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public Column nextColumn() {
        return new Column(this.getColumnIndex()+1).setHeader(header);
    }

    public Column nextLine() {
        return new Column().setHeader(header);
    }

    public Column setHeader(Header header) {
        this.header = header;
        if (this.header != null) {
            this.columnName = header.getName(this.columnIndex);
        }
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Column)) {
            return false;
        }
        Column column = (Column)obj;
        if (this.columnName != null) {
            if (this.columnName.equals(column.columnName)) {
                return true;
            }
        }
        else if (this.columnIndex != -1) {
            if (this.columnIndex == column.columnIndex) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Index: "+this.columnIndex+", Name: "+this.columnName+", Header: "+(header!=null?"true":"false");
    }

}
