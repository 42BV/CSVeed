package org.csveed.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.TreeMap;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.row.HeaderImpl;
import org.csveed.row.LineWithInfo;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Test
    public void excelColumnToColumnIndex() {
        Column excel = new ColumnExcel("AH");
        assertEquals(34, excel.getColumnIndex());
    }

    @Test
    public void largestPossibleIndex() {
        Column excel = new ColumnExcel("ZZ");
        assertEquals(702, excel.getColumnIndex());
    }

    @Test
    public void columnIndexToExcelColumn() {
        Column excel = new Column(34);
        assertEquals("AH", excel.getExcelColumn());
    }

    @Test
    public void wrongIndex() {
        assertThrows(CsvException.class, () ->  {
            new Column(0);
        });
    }

    @Test
    public void nextColumn() {
        Column column = new Column(3);
        assertEquals(4, column.nextColumn().getColumnIndex());
    }

    @Test
    public void reset() {
        Column column = new Column(3);
        assertEquals(Column.FIRST_COLUMN_INDEX, column.nextLine().getColumnIndex());
    }

    @Test
    public void equals() {
        assertEquals(new Column(3), new Column(3));
    }

    @Test
    public void treeMap() {
        Map<Column, String> map = new TreeMap<>();
        Column storeColumn = new Column("name");
        map.put(storeColumn, "alpha");
        LineWithInfo line = new LineWithInfo();
        line.addCell("name");
        Header header = new HeaderImpl(line);
        Column searchColumn = new Column().setHeader(header);
        assertNotNull(map.get(searchColumn));
    }

    @Test
    public void treeMapWithColumnIndex() {
        Map<Column, String> map = new TreeMap<>();
        map.put(new Column(1), "alpha");
        map.put(new Column(2), "beta");
        map.put(new Column(3), "gamma");
        assertEquals("alpha", map.get(new Column(1)));
        assertEquals("beta", map.get(new Column(2)));
        assertEquals("gamma", map.get(new Column(3)));
    }

}