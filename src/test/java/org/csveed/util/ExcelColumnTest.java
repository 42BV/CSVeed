package org.csveed.util;

import org.csveed.report.CsvException;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ExcelColumnTest {


    @Test
    public void excelColumnToColumnIndex() {
        ExcelColumn excel = new ExcelColumn("AH");
        assertEquals(34, excel.getColumnIndex());
    }

    @Test
    public void largestPossibleIndex() {
        ExcelColumn excel = new ExcelColumn("ZZ");
        assertEquals(702, excel.getColumnIndex());
    }

    @Test
    public void columnIndexToExcelColumn() {
        ExcelColumn excel = new ExcelColumn(34);
        assertEquals("AH", excel.getExcelColumn());
    }

    @Test(expected = CsvException.class)
    public void wrongIndex() {
        ExcelColumn excel = new ExcelColumn(0);
        excel.getExcelColumn();
    }

    @Test
    public void nextColumn() {
        ExcelColumn column = new ExcelColumn(3);
        assertEquals(4, column.nextColumn().getColumnIndex());
    }

    @Test
    public void reset() {
        ExcelColumn column = new ExcelColumn(3);
        assertEquals(ExcelColumn.FIRST_COLUMN_INDEX, column.nextLine().getColumnIndex());
    }

    @Test
    public void equals() {
        assertEquals(new ExcelColumn(3), new ExcelColumn(3));
    }

}
