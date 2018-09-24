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

public class RowWriterTest {

    @Test
    public void readAndWrite() throws IOException {
        // First read...
        Reader reader = new StringReader(
                "alpha;beta;gamma\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
        );
        RowReader lineReader = new RowReaderImpl(reader);
        List<Row> rows = lineReader.readRows();

        // ... then write
        StringWriter writer = new StringWriter();
        RowWriter rowWriter = new RowWriterImpl(writer);
        rowWriter.writeHeader(rows.get(0).getHeader());
        rowWriter.writeRows(rows);

        writer.close();
        assertEquals(
                "\"alpha\";\"beta\";\"gamma\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void writeMultipleRows() throws IOException {
        StringWriter writer = new StringWriter();
        RowWriter rowWriter = new RowWriterImpl(writer);
        rowWriter.writeHeader(new String[]
                { "desc1", "desc2", "desc3" } );
        rowWriter.writeRows(new String[][]{
                {"alpha", "beta", "gamma"},
                {"delta", "epsilon", "phi"},
                {"b1", "b2", "b3"}
        });
        writer.close();
        assertEquals(
                "\"desc1\";\"desc2\";\"desc3\"\r\n"+
                "\"alpha\";\"beta\";\"gamma\"\r\n"+
                "\"delta\";\"epsilon\";\"phi\"\r\n"+
                "\"b1\";\"b2\";\"b3\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void writeRowWithEscapeCharacters() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false)
                .setEscape('\\');
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "\"tekst met \"quotes\"\"" } );
        writer.close();
        assertEquals("\"\\\"tekst met \\\"quotes\\\"\\\"\"\r\n", writer.getBuffer().toString());
    }

    @Test
    public void writeRow() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false);
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals("\"alpha\";\"beta\";\"gamma\"\r\n", writer.getBuffer().toString());
    }

    @Test
    public void writeRowWithoutQuoting() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false)
                .setQuotingEnabled(false);
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals("alpha;beta;gamma\r\n", writer.getBuffer().toString());
    }

    @Test
    public void writeRowWithoutQuotingAndEscaping() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false)
                .setQuotingEnabled(false);
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "\"tekst met \"quotes\"\"" } );
        writer.close();
        assertEquals("\"tekst met \"quotes\"\"\r\n", writer.getBuffer().toString());
    }


    @Test
    public void writeRowWithNullValue() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false);
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "alpha", null, "gamma" } );
        writer.close();
        assertEquals("\"alpha\";\"\";\"gamma\"\r\n", writer.getBuffer().toString());
    }

    @Test
    public void writeRowAndHeader() throws IOException {
        StringWriter writer = new StringWriter();
        RowWriter rowWriter = new RowWriterImpl(writer);
        rowWriter.writeHeader(new String[] { "desc1", "desc2", "desc3" } );
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals(
                "\"desc1\";\"desc2\";\"desc3\"\r\n"+
                "\"alpha\";\"beta\";\"gamma\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void noHeaderWritten() {
        RowWriter rowWriter = new RowWriterImpl(new StringWriter());
        assertThrows(CsvException.class, () ->  {
            rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        });
    }

}
