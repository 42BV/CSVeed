package org.csveed.row;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static junit.framework.Assert.assertEquals;

public class RowWriterTest {

    @Test
    public void writeRow() throws IOException {
        StringWriter writer = new StringWriter();
        RowInstructions instructions = new RowInstructionsImpl()
                .setUseHeader(false);
        RowWriter rowWriter = new RowWriterImpl(writer, instructions);
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals("\"alpha\";\"beta\";\"gamma\"", writer.getBuffer().toString());
    }

    @Test
    public void writeRowAndHeader() throws IOException {
        StringWriter writer = new StringWriter();
        RowWriter rowWriter = new RowWriterImpl(writer);
        rowWriter.writeHeader(new String[] { "desc1", "desc2", "desc3" } );
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals(
                "\"desc1\";\"desc2\";\"desc3\"\r"+
                "\"alpha\";\"beta\";\"gamma\"\r",
                writer.getBuffer().toString());
    }

    @Test(expected = RowWriteException.class)
    public void noHeaderWritten() {
        RowWriter rowWriter = new RowWriterImpl(new StringWriter());
        rowWriter.writeRow(new String[] { "alpha", "beta", "gamma" } );
    }

}
