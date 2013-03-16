package org.csveed.util;

public class ExcelColumn {

    private int columnIndex;

    public ExcelColumn(String excelColumn) {
        this.columnIndex = excelColumnToColumnIndex(excelColumn);
    }

    public ExcelColumn(int columnIndex) {
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
            excelColumn.insert(0, (char)(columnIndex % 26 + 'A' - 1));
            columnIndex /= 26;
        }
        return excelColumn.toString();
    }

}
