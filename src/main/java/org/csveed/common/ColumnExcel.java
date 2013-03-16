package org.csveed.common;

public class ColumnExcel extends ColumnIndex {

    public ColumnExcel(String columnExcel) {
        super(excelColumnToColumnIndex(columnExcel));
    }

    private static int excelColumnToColumnIndex(String excelColumn) {
        excelColumn = excelColumn.toUpperCase();
        int sum = 0;
        for (char currentChar : excelColumn.toCharArray()) {
            sum *= 26;
            sum += currentChar - 'A' + 1;
        }
        return sum;
    }

}
