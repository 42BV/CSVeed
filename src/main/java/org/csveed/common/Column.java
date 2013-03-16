package org.csveed.common;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

public class Column implements Comparable<Column> {

    public static final int FIRST_COLUMN_INDEX = 1;

    private int columnIndex = -1;

    private String columnName = null;

    private Header header;

    private ColumnKey key;

    public Column(String columnName) {
        setColumnName(columnName);
    }

    public Column() {
        this(FIRST_COLUMN_INDEX);
    }

    public Column(int columnIndex) {
        if (columnIndex <= 0) {
            throw new CsvException(new GeneralError("Column index cannot be set at 0 or lower. Column indexes are 1-based"));
        }
        setColumnIndex(columnIndex);
    }

    private String columnIndexToExcelColumn(int columnIndex) {
        StringBuilder excelColumn = new StringBuilder();
        while (columnIndex % 26 > 0) {
            excelColumn.insert(0, (char) (columnIndex % 26 + 'A' - 1));
            columnIndex /= 26;
        }
        return excelColumn.toString();
    }

    public Column setHeader(Header header) {
        this.header = header;
        if (this.header != null) {
            setColumnName(header.getName(this.columnIndex));
        }
        return this;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
        setKey(new ColumnIndexKey(this.columnIndex));
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName.toLowerCase();
        setKey(new ColumnNameKey(this.columnName));
    }

    public void setKey(ColumnKey key) {
        this.key = key;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Column)) {
            return false;
        }
        return this.key.equals(((Column)obj).key);
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return this.key.toString();
    }

    @Override
    public int compareTo(Column column) {
        return this.key.compareTo(column.key);
    }
}
