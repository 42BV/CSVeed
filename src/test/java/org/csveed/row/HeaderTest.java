package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.junit.Test;

public class HeaderTest {

    @Test(expected = CsvException.class)
    public void getNonExistingColumnName() {
        Header header =new HeaderImpl(createLine("alpha"));
        header.getIndex("does-not-exist");
    }

    @Test(expected = CsvException.class)
    public void getNonExistingColumnIndex() {
        Header header =new HeaderImpl(createLine("alpha"));
        header.getName(13);
    }

    protected LineWithInfo createLine(String cell) {
        LineWithInfo line = new LineWithInfo();
        line.addCell(cell);
        return line;
    }
}
