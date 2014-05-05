package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.api.Row;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

public class RowWriterImpl implements RowWriter {

    private final Writer writer;

    private RowInstructions rowInstructions;

    private Header header;

    public RowWriterImpl(Writer writer) {
        this(writer, new RowInstructionsImpl());
    }

    public RowWriterImpl(Writer writer, RowInstructions rowInstructions) {
        this.writer = writer;
        this.rowInstructions = rowInstructions;
    }

    @Override
    public void writeRows(String[][] rows) {
        for (String[] row : rows) {
            writeRow(row);
        }
    }

    @Override
    public void writeRows(Collection<Row> rows) {
        for (Row row : rows) {
            writeRow(row);
        }
    }

    @Override
    public void writeRow(String[] cells) {
        writeRow(new RowImpl(convertToLine(cells), header));
    }

    @Override
    public Row writeRow(Row row) {
        if (rowInstructions.isUseHeader() && this.header == null) {
            throw new RowWriteException("Header has not been set for this table");
        }
        writeCells(row.iterator());
        return row;
    }

    @Override
    public Header writeHeader(String[] headerNames) {
        Header header = new HeaderImpl(convertToLine(headerNames));
        writeHeader(header);
        return header;
    }

    @Override
    public void writeHeader(Header header) {
        this.header = header;
        writeCells(header.iterator());
    }

    @Override
    public RowInstructions getRowInstructions() {
        return this.rowInstructions;
    }

    private void writeCells(Iterator<String> cells) {
        boolean firstCell = true;
        try {
            while (cells.hasNext()) {
                String cell = cells.next();
                if (!firstCell) {
                    writeSeparator();
                }
                firstCell = false;
                writeCell(cell);
            }
            writeEOL();
        } catch(IOException err){
            throw new RowWriteException(err, "Error in writing to the writer: " + err.getMessage());
        }
    }

    private void writeEOL() throws IOException {
        writer.write(rowInstructions.getEndOfLine());
    }

    private void writeSeparator() throws IOException {
        writer.write(rowInstructions.getSeparator());
    }

    private void writeCell(String cell) throws IOException {
        writer.write(rowInstructions.getQuote());
        String searchString = Character.toString(rowInstructions.getQuote());
        String replaceString = new String(new char[] { rowInstructions.getEscape(), rowInstructions.getQuote() } );
        writer.write(cell.replace(searchString, replaceString));
        writer.write(rowInstructions.getQuote());
    }

    private LineWithInfo convertToLine(String[] cells) {
        LineWithInfo line = new LineWithInfo();
        for (String cell : cells) {
            line.addCell(cell);
        }
        return line;
    }

}
