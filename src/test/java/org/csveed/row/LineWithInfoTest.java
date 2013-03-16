package org.csveed.row;

import org.csveed.report.CsvException;
import org.csveed.report.RowReport;
import org.csveed.util.ExcelColumn;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class LineWithInfoTest {

    @Test
    public void cellIsNull() {
        LineWithInfo row = new LineWithInfo();
        row.addCell(null);
        assertEquals(0, row.getCellPosition(new ExcelColumn()).getStart());
        assertEquals(0, row.getCellPosition(new ExcelColumn()).getEnd());
    }

    @Test
    public void cellIsEmpty() {
        LineWithInfo row = new LineWithInfo();
        row.addCell("");
        assertEquals(0, row.getCellPosition(new ExcelColumn()).getStart());
        assertEquals(0, row.getCellPosition(new ExcelColumn()).getEnd());
    }

    @Test
    public void convertCharacters() {
        LineWithInfo row = new LineWithInfo();
        assertEquals("\\b", row.convertToPrintable('\b'));
        assertEquals("\\f", row.convertToPrintable('\f'));
    }

    @Test
    public void nonExistingCell() {
        LineWithInfo row = new LineWithInfo();
        assertNull(row.reportOnColumn(new ExcelColumn()));
    }

    @Test(expected = CsvException.class)
    public void getReportOnColumnIndex0() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Hello");
        RowReport report = row.reportOnColumn(new ExcelColumn(0));
    }

    @Test
    public void simpleWord() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Hello");
        RowReport report = row.reportOnColumn(new ExcelColumn());
        assertEquals("Hello", report.getRow());
        assertEquals(0, report.getStart());
        assertEquals(5, report.getEnd());
    }

    @Test
    public void coupleOfWords() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter(';');
        row = addString(row, "Beta");
        row.addCharacter(';');
        row = addString(row, "Gamma");
        RowReport report = row.reportOnColumn(new ExcelColumn(3));
        assertEquals("Alpha;Beta;Gamma", report.getRow());
        assertEquals(11, report.getStart());
        assertEquals(16, report.getEnd());
    }

    @Test
    public void variousNonPrintables() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter('\t');
        row = addString(row, "Beta");
        row.addCharacter('\t');
        row = addString(row, "Gamma");
        RowReport report = row.reportOnColumn(new ExcelColumn(3));
        assertEquals("Alpha\\tBeta\\tGamma", report.getRow());
        assertEquals(13, report.getStart());
        assertEquals(18, report.getEnd());
    }

    @Test
    public void reportOnEndOfLine() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter('\t');
        row = addString(row, "Beta");
        row.addCharacter('@');
        RowReport report = row.reportOnEndOfLine();
        assertEquals("Alpha\\tBeta@", report.getRow());
        assertEquals(12, report.getStart());
        assertEquals(12, report.getEnd());
    }

    protected LineWithInfo addString(LineWithInfo row, String text) {
        row.markStartOfColumn();
        for (char character : text.toCharArray()) {
            row.addCharacter(character);
        }
        row.addCell(text);
        return row;
    }

}
