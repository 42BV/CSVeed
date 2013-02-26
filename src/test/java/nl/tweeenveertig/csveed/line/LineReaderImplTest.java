package nl.tweeenveertig.csveed.line;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.report.RowReport;
import nl.tweeenveertig.csveed.token.EncounteredSymbol;
import nl.tweeenveertig.csveed.token.SymbolMapping;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class LineReaderImplTest {

    @Test(expected = CsvException.class)
    public void dissimilarNumberOfColumns() {
        Reader reader = new StringReader(
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
            "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
            "\"row 3, cell 1\";\"row 3, cell 2\""
        );
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        lineReader.read();
    }

    @Test
    public void readUnmapped() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\""
        );
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        List<Row> rows = lineReader.read();
        assertEquals(3, rows.size());
    }

    @Test
    public void nonContentBeforeLines() {
        Reader reader = new StringReader(
                "# line 1\n"+
                "# line 2\n"+
                "# line 3\n"+
                "alpha;beta;gamma\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
        );
        LineReaderImpl lineReader = new LineReaderImpl(
            reader,
            new LineReaderInstructionsImpl()
                .setStartRow(3)
                .setUseHeader(true)
        );
        List<Row> rows = lineReader.read();
        assertEquals(2, rows.size());
    }

    @Test
    public void roughRide() throws IOException {
        Reader reader = new StringReader("\"alpha\";\"\";;\"beta\";gamma;\"een \"\"echte\"\" test\";\"1\n2\n3\n\"\"regels\"\"\"");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line cells = lineReader.readBareLine();
        assertEquals(7, cells.size());
        assertEquals("alpha", cells.get(0));
        assertEquals("", cells.get(1));
        assertEquals("", cells.get(2));
        assertEquals("beta", cells.get(3));
        assertEquals("gamma", cells.get(4));
        assertEquals("een \"echte\" test", cells.get(5));
        assertEquals("1\n2\n3\n\"regels\"", cells.get(6));
    }

    @Test
    public void doubleQuotesAsEscape() throws IOException {
        Reader reader = new StringReader("\"\"\"very literal\"\"\";\"a\"\"b\"\"c\"\n\"abc\";\"first this, \"\"then that\"\"\"");
        LineReaderImpl lineReader = new LineReaderImpl(
            reader,
            new LineReaderInstructionsImpl()
                .setUseHeader(false)
        );
        checkEscapedStrings(lineReader.read());
    }

    @Test
    public void backSlashesAsEscape() throws IOException {
        Reader reader = new StringReader("\"\\\"very literal\\\"\";\"a\\\"b\\\"c\"\n\"abc\";\"first this, \\\"then that\\\"\"");
        LineReaderImpl lineReader = new LineReaderImpl(
            reader,
            new LineReaderInstructionsImpl()
                .setUseHeader(false)
                .setEscape('\\')
        );
        checkEscapedStrings(lineReader.read());
    }

    private void checkEscapedStrings(List<Row> lines) {
        Row row = lines.get(0);
        assertEquals("\"very literal\"", row.get(0));
        assertEquals("a\"b\"c", row.get(1));
        row = lines.get(1);
        assertEquals("abc", row.get(0));
        assertEquals("first this, \"then that\"", row.get(1));
    }

    @Test
    public void readAllLines() throws IOException {
        Reader reader = new StringReader(";;;\n;;;\n;;;\n");
        LineReaderImpl lineReader = new LineReaderImpl(
            reader,
            new LineReaderInstructionsImpl()
                .setUseHeader(false)
        );
        List<Row> allLines = lineReader.read();
        assertEquals(3, allLines.size());
    }

    @Test
    public void allNumbers() throws IOException {
        Reader reader = new StringReader("17.51;23.19;-100.23;");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(4, row.size());
        assertEquals("17.51", row.get(0));
        assertEquals("23.19", row.get(1));
        assertEquals("-100.23", row.get(2));
        assertEquals("", row.get(3));
    }

    @Test
    public void spacesBeforeAndAfter() {
        Reader reader = new StringReader("    \"alpha\"  ; \"beta\"   ; \"gamma\" ");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(3, row.size());
        assertEquals("alpha", row.get(0));
        assertEquals("beta", row.get(1));
        assertEquals("gamma", row.get(2));
    }

    @Test
    public void spaceWithoutQuotesFields() {
        Reader reader = new StringReader("    alpha one  ; beta   ; gamma ");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(3, row.size());
        assertEquals("alpha one", row.get(0));
        assertEquals("beta", row.get(1));
        assertEquals("gamma", row.get(2));
    }

    @Test
    public void reportSimple() {
        Reader reader = new StringReader("17.51;23.19;-100.23");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line row = lineReader.readBareLine();
        RowReport report = row.reportOnColumn(2);
        assertEquals("17.51;23.19;-100.23[EOF]", report.getPrintableLines().get(0));
        assertEquals("            ^-----^     ", report.getPrintableLines().get(1));
    }

    @Test
    public void reportEscapingAndQuotes() {
        Reader reader = new StringReader("\"alpha\";\"\";;\"b\te\tt\ta\";gamma;\"een \"\"echte\"\" test\";\"1\n2\n3\n\"\"regels\"\"\"");
        LineReaderImpl lineReader = new LineReaderImpl(reader);
        Line row = lineReader.readBareLine();
        RowReport report = row.reportOnColumn(3);
        assertEquals("\"alpha\";\"\";;\"b\\te\\tt\\ta\";gamma;\"een \"\"echte\"\" test\";\"1\\n2\\n3\\n\"\"regels\"\"\"[EOF]", report.getPrintableLines().get(0));
        assertEquals("             ^---------^                                                      ", report.getPrintableLines().get(1));
        report = row.reportOnColumn(2);
        assertEquals("           ^                                                                  ", report.getPrintableLines().get(1));
    }

}
