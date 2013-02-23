package nl.tweeenveertig.csveed.line;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class HeaderTest {

    @Test
    public void checkNumberOfColumns() {
        Header header = new Header(getRow("alpha;beta;gamma"));
        assertFalse(header.checkLine(getRow("1;2;3;4")));
        assertTrue(header.checkLine(getRow("1;2;3")));
    }

    public Line getRow(String row) {
        Reader reader = new StringReader(row);
        LineReaderImpl lineReader = new LineReaderImpl();
        return lineReader.readBareLine(reader);
    }
}
