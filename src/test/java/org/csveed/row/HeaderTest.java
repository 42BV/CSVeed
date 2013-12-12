package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeaderTest {

    @Test(expected = CsvException.class)
    public void getNonExistingColumnName() {
        Header header = new HeaderImpl(createLine("alpha"));
        header.getIndex("does-not-exist");
    }

    @Test(expected = CsvException.class)
    public void getNonExistingColumnIndex() {
        Header header = new HeaderImpl(createLine("alpha"));
        header.getName(13);
    }

    @Test
    public void toLowerCase() {
        Header header = new HeaderImpl(createLine("Alpha"));
        assertEquals("Alpha", header.getName(1));
        assertEquals(1, header.getIndex("Alpha"));
    }

    protected LineWithInfo createLine(String cell) {
        LineWithInfo line = new LineWithInfo();
        line.addCell(cell);
        return line;
    }
}
