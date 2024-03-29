/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.row;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowReport;
import org.junit.jupiter.api.Test;

/**
 * The Class RowReaderTest.
 */
class RowReaderTest {

    /**
     * Column indexes same one based approach.
     */
    @Test
    void columnIndexesSameOneBasedApproach() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Row row = lineReader.readRow();
        assertEquals("alpha", row.getColumnName(1));
        assertEquals("row 1, cell 1", row.get(1));
    }

    /**
     * Gets the by column name.
     */
    @Test
    void getByColumnName() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Row row = lineReader.readRow();
        assertEquals("row 1, cell 2", row.get("beta"));
    }

    /**
     * Empty lines.
     */
    @Test
    void emptyLines() {
        Reader reader = new StringReader(
                "\n" + "\n" + "\n" + "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                        + "\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        assertEquals(2, lineReader.readRows().size());
    }

    /**
     * Do not skip empty lines.
     */
    @Test
    void doNotSkipEmptyLines() {
        Reader reader = new StringReader("alpha\n" + "\n" + "word\n" + "\n" + "\n");
        RowReaderImpl lineReader = new RowReaderImpl(reader, new RowInstructionsImpl().skipEmptyLines(false));
        assertEquals(5, lineReader.readRows().size());
    }

    /**
     * Gets the column index at 0.
     */
    @Test
    void getColumnIndexAt0() {
        Reader reader = new StringReader("alpha;beta;gamma");
        RowReaderImpl rowReader = new RowReaderImpl(reader);
        Header header = rowReader.getHeader();
        assertThrows(CsvException.class, () -> header.getName(0));
    }

    /**
     * Comment line.
     */
    @Test
    void commentLine() {
        Reader reader = new StringReader("# lots of text\n" + "# bla...\n" + "# more bla...\n" + "alpha;beta;gamma\n"
                + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n" + "# this line must be ignored!\n"
                + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"");
        RowReaderImpl rowReader = new RowReaderImpl(reader);
        assertEquals(2, rowReader.readRows().size());
        Header header = rowReader.getHeader();
        assertEquals("alpha", header.getName(1));
    }

    /**
     * Dissimilar number of columns.
     */
    @Test
    void dissimilarNumberOfColumns() {
        Reader reader = new StringReader("\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n" + "\"row 3, cell 1\";\"row 3, cell 2\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        assertThrows(CsvException.class, () -> lineReader.readRows());
    }

    /**
     * Read unmapped.
     */
    @Test
    void readUnmapped() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                        + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
                        + "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        List<Row> rows = lineReader.readRows();
        assertEquals(3, rows.size());
    }

    /**
     * Non content before lines.
     */
    @Test
    void nonContentBeforeLines() {
        Reader reader = new StringReader("# line 1\n" + "# line 2\n" + "# line 3\n" + "alpha;beta;gamma\n"
                + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n");
        RowReaderImpl lineReader = new RowReaderImpl(reader,
                new RowInstructionsImpl().setStartRow(4).setUseHeader(true));
        List<Row> rows = lineReader.readRows();
        assertEquals(2, rows.size());
    }

    /**
     * Rough ride.
     */
    @Test
    void roughRide() {
        Reader reader = new StringReader(
                "\"alpha\";\"\";;\"beta\";gamma;\"een \"\"echte\"\" test\";\"1\n2\n3\n\"\"regels\"\"\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
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

    /**
     * Double quotes as escape.
     */
    @Test
    void doubleQuotesAsEscape() {
        Reader reader = new StringReader(
                "\"\"\"very literal\"\"\";\"a\"\"b\"\"c\"\n\"abc\";\"first this, \"\"then that\"\"\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader, new RowInstructionsImpl().setUseHeader(false));
        checkEscapedStrings(lineReader.readRows());
    }

    /**
     * Back slashes as escape.
     */
    @Test
    void backSlashesAsEscape() {
        Reader reader = new StringReader(
                "\"\\\"very literal\\\"\";\"a\\\"b\\\"c\"\n\"abc\";\"first this, \\\"then that\\\"\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader,
                new RowInstructionsImpl().setUseHeader(false).setEscape('\\'));
        checkEscapedStrings(lineReader.readRows());
    }

    /**
     * Check escaped strings.
     *
     * @param lines
     *            the lines
     */
    private void checkEscapedStrings(List<Row> lines) {
        Row row = lines.get(0);
        assertEquals("\"very literal\"", row.get(1));
        assertEquals("a\"b\"c", row.get(2));
        row = lines.get(1);
        assertEquals("abc", row.get(1));
        assertEquals("first this, \"then that\"", row.get(2));
    }

    /**
     * Read all lines.
     */
    @Test
    void readAllLines() {
        Reader reader = new StringReader(";;;\n;;;\n;;;\n");
        RowReaderImpl lineReader = new RowReaderImpl(reader, new RowInstructionsImpl().setUseHeader(false));
        List<Row> allLines = lineReader.readRows();
        assertEquals(3, allLines.size());
    }

    /**
     * All numbers.
     */
    @Test
    void allNumbers() {
        Reader reader = new StringReader("17.51;23.19;-100.23;");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(4, row.size());
        assertEquals("17.51", row.get(0));
        assertEquals("23.19", row.get(1));
        assertEquals("-100.23", row.get(2));
        assertEquals("", row.get(3));
    }

    /**
     * Spaces before and after.
     */
    @Test
    void spacesBeforeAndAfter() {
        Reader reader = new StringReader("    \"alpha\"  ; \"beta\"   ; \"gamma\" ");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(3, row.size());
        assertEquals("alpha", row.get(0));
        assertEquals("beta", row.get(1));
        assertEquals("gamma", row.get(2));
    }

    /**
     * Space without quotes fields.
     */
    @Test
    void spaceWithoutQuotesFields() {
        Reader reader = new StringReader("    alpha one  ; beta   ; gamma ");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Line row = lineReader.readBareLine();
        assertEquals(3, row.size());
        assertEquals("alpha one", row.get(0));
        assertEquals("beta", row.get(1));
        assertEquals("gamma", row.get(2));
    }

    /**
     * Report simple.
     */
    @Test
    void reportSimple() {
        Reader reader = new StringReader("17.51;23.19;-100.23");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Line row = lineReader.readBareLine();
        RowReport report = row.reportOnColumn(new Column(3));
        assertEquals("17.51;23.19;-100.23[EOF]", report.getPrintableLines().get(0));
        assertEquals("            ^-----^     ", report.getPrintableLines().get(1));
    }

    /**
     * Report escaping and quotes.
     */
    @Test
    void reportEscapingAndQuotes() {
        Reader reader = new StringReader(
                "\"alpha\";\"\";;\"b\te\tt\ta\";gamma;\"een \"\"echte\"\" test\";\"1\n2\n3\n\"\"regels\"\"\"");
        RowReaderImpl lineReader = new RowReaderImpl(reader);
        Line row = lineReader.readBareLine();
        RowReport report = row.reportOnColumn(new Column(4));
        assertEquals(
                "\"alpha\";\"\";;\"b\\te\\tt\\ta\";gamma;\"een \"\"echte\"\" test\";\"1\\n2\\n3\\n\"\"regels\"\"\"[EOF]",
                report.getPrintableLines().get(0));
        assertEquals("             ^---------^                                                      ",
                report.getPrintableLines().get(1));
        report = row.reportOnColumn(new Column(3));
        assertEquals("           ^                                                                  ",
                report.getPrintableLines().get(1));
    }

    /**
     * Read header.
     */
    @Test
    void readHeader() {
        Reader reader = new StringReader("alpha;beta;gamma");
        RowReader rowReader = new RowReaderImpl(reader);
        Header header = rowReader.readHeader();
        assertNotNull(header);
        assertEquals(3, header.size());
    }

    /**
     * Read header second line.
     */
    @Test
    void readHeaderSecondLine() {
        Reader reader = new StringReader("alpha;beta;gamma\nalpha2;beta2");
        RowReader rowReader = new RowReaderImpl(reader, new RowInstructionsImpl().setStartRow(2));
        Header header = rowReader.readHeader();
        assertNotNull(header);
        assertEquals(2, header.size());
    }

    /**
     * Read header without use header.
     */
    @Test
    void readHeaderWithoutUseHeader() {
        Reader reader = new StringReader("alpha;beta;gamma");
        RowReader rowReader = new RowReaderImpl(reader, new RowInstructionsImpl().setUseHeader(false));
        Header header = rowReader.readHeader();
        assertNotNull(header);
        assertEquals(3, header.size());
    }

}
