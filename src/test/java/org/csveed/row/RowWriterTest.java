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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.junit.jupiter.api.Test;

/**
 * The Class RowWriterTest.
 */
class RowWriterTest {

    /**
     * Read and write.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void readAndWrite() throws IOException {
        // First read...
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                        + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n");
        RowReader lineReader = new RowReaderImpl(reader);
        List<Row> rows = lineReader.readRows();

        // ... then write
        try (StringWriter writer = new StringWriter()) {
            RowWriter rowWriter = new RowWriterImpl(writer);
            rowWriter.writeHeader(rows.get(0).getHeader());
            rowWriter.writeRows(rows);

            assertEquals(
                    "\"alpha\";\"beta\";\"gamma\"\r\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"
                            + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n",
                    writer.getBuffer().toString());
        }
    }

    /**
     * Write multiple rows.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeMultipleRows() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowWriter rowWriter = new RowWriterImpl(writer);
            rowWriter.writeHeader(new String[] { "desc1", "desc2", "desc3" });
            rowWriter.writeRows(new String[][] { { "alpha", "beta", "gamma" }, { "delta", "epsilon", "phi" },
                    { "b1", "b2", "b3" } });

            assertEquals(
                    "\"desc1\";\"desc2\";\"desc3\"\r\n" + "\"alpha\";\"beta\";\"gamma\"\r\n"
                            + "\"delta\";\"epsilon\";\"phi\"\r\n" + "\"b1\";\"b2\";\"b3\"\r\n",
                    writer.getBuffer().toString());
        }
    }

    /**
     * Write row with escape characters.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRowWithEscapeCharacters() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowInstructions instructions = new RowInstructionsImpl().setUseHeader(false).setEscape('\\');
            RowWriter rowWriter = new RowWriterImpl(writer, instructions);
            rowWriter.writeRow(new String[] { "\"tekst met \"quotes\"\"" });

            assertEquals("\"\\\"tekst met \\\"quotes\\\"\\\"\"\r\n", writer.getBuffer().toString());
        }
    }

    /**
     * Write row.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRow() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowInstructions instructions = new RowInstructionsImpl().setUseHeader(false);
            RowWriter rowWriter = new RowWriterImpl(writer, instructions);
            rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" });

            assertEquals("\"alpha\";\"beta\";\"gamma\"\r\n", writer.getBuffer().toString());
        }
    }

    /**
     * Write row without quoting.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRowWithoutQuoting() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowInstructions instructions = new RowInstructionsImpl().setUseHeader(false).setQuotingEnabled(false);
            RowWriter rowWriter = new RowWriterImpl(writer, instructions);
            rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" });

            assertEquals("alpha;beta;gamma\r\n", writer.getBuffer().toString());
        }
    }

    /**
     * Write row without quoting and escaping.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRowWithoutQuotingAndEscaping() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowInstructions instructions = new RowInstructionsImpl().setUseHeader(false).setQuotingEnabled(false);
            RowWriter rowWriter = new RowWriterImpl(writer, instructions);
            rowWriter.writeRow(new String[] { "\"tekst met \"quotes\"\"" });

            assertEquals("\"tekst met \"quotes\"\"\r\n", writer.getBuffer().toString());
        }
    }

    /**
     * Write row with null value.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRowWithNullValue() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowInstructions instructions = new RowInstructionsImpl().setUseHeader(false);
            RowWriter rowWriter = new RowWriterImpl(writer, instructions);
            rowWriter.writeRow(new String[] { "alpha", null, "gamma" });

            assertEquals("\"alpha\";\"\";\"gamma\"\r\n", writer.getBuffer().toString());
        }
    }

    /**
     * Write row and header.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    void writeRowAndHeader() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            RowWriter rowWriter = new RowWriterImpl(writer);
            rowWriter.writeHeader(new String[] { "desc1", "desc2", "desc3" });
            rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" });

            assertEquals("\"desc1\";\"desc2\";\"desc3\"\r\n" + "\"alpha\";\"beta\";\"gamma\"\r\n",
                    writer.getBuffer().toString());
        }
    }

    /**
     * No header written.
     */
    @Test
    void noHeaderWritten() {
        RowWriter rowWriter = new RowWriterImpl(new StringWriter());
        assertThrows(CsvException.class, () -> rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" }));
    }

}
