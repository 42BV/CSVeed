package org.csveed.util;

import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

public class ExcelColumn {

    public static final int FIRST_COLUMN_INDEX = 1;

    private final int columnIndex;

    public ExcelColumn() {
        this(FIRST_COLUMN_INDEX);
    }

    public ExcelColumn(String excelColumn) {
        this.columnIndex = excelColumnToColumnIndex(excelColumn);
    }

    public ExcelColumn(int columnIndex) {
        if (columnIndex <= 0) {
            throw new CsvException(new GeneralError("Column index cannot be set at 0 or lower. Column indexes are 1-based"));
        }
        this.columnIndex = columnIndex;
    }

    public String getExcelColumn() {
        return columnIndexToExcelColumn(this.columnIndex);
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    private int excelColumnToColumnIndex(String excelColumn) {
        excelColumn = excelColumn.toUpperCase();
        int sum = 0;
        for (char currentChar : excelColumn.toCharArray()) {
            sum *= 26;
            sum += currentChar - 'A' + 1;
        }
        return sum;
    }

    private String columnIndexToExcelColumn(int columnIndex) {
        StringBuilder excelColumn = new StringBuilder();
        while (columnIndex % 26 > 0) {
            excelColumn.insert(0, (char) (columnIndex % 26 + 'A' - 1));
            columnIndex /= 26;
        }
        return excelColumn.toString();
    }

    public ExcelColumn nextColumn() {
        return new ExcelColumn(this.getColumnIndex()+1);
    }

    public ExcelColumn nextLine() {
        return new ExcelColumn();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExcelColumn)) {
            return false;
        }
        ExcelColumn column = (ExcelColumn)obj;
        return this.columnIndex == column.getColumnIndex();
    }

    @Override
    public int hashCode() {
        return getColumnIndex() % 10;
    }

}
