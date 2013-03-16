package org.csveed.util;

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

    @Test
    public void wrongIndex() {
        ExcelColumn excel = new ExcelColumn(0);
        assertEquals("", excel.getExcelColumn());
    }

}
