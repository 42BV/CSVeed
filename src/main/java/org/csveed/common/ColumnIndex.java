package org.csveed.common;

import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

public class ColumnIndex extends Column {

    private final int columnIndex;

    public static final int FIRST_COLUMN_INDEX = 1;

    public ColumnIndex() {
        this(FIRST_COLUMN_INDEX);
    }

    public ColumnIndex(int columnIndex) {
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

    @Override
    public String getExcelColumn() {
        return columnIndexToExcelColumn(this.columnIndex);
    }

    @Override
    public int getColumnIndex() {
        return this.columnIndex;
    }

    @Override
    public Column nextColumn() {
        return new ColumnIndex(this.getColumnIndex()+1);
    }

    @Override
    public Column nextLine() {
        return new ColumnIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Column)) {
            return false;
        }
        Column column = (Column)obj;
        return this.columnIndex == column.getColumnIndex();
    }

    @Override
    public int hashCode() {
        return getColumnIndex() % 10;
    }

}
