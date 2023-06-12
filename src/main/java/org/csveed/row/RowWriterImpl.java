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

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RowWriterImpl.
 */
public class RowWriterImpl implements RowWriter {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RowWriterImpl.class);

    /** The writer. */
    private final Writer writer;

    /** The row instructions. */
    private RowInstructions rowInstructions;

    /** The header. */
    private Header header;

    /**
     * Instantiates a new row writer impl.
     *
     * @param writer
     *            the writer
     */
    public RowWriterImpl(Writer writer) {
        this(writer, new RowInstructionsImpl());
    }

    /**
     * Instantiates a new row writer impl.
     *
     * @param writer
     *            the writer
     * @param rowInstructions
     *            the row instructions
     */
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
    public Row writeRow(String[] cells) {
        Row row = new RowImpl(convertToLine(cells), header);
        writeRow(row);
        return row;
    }

    @Override
    public void writeRow(Row row) {
        if (rowInstructions.isUseHeader() && this.header == null) {
            throw new CsvException(
                    new GeneralError("Header has not been set for this table. Make sure to write it or configure "
                            + "it to be not used: .setUseHeader(false)"));
        }
        writeCells(row.iterator());
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

    /**
     * Write cells.
     *
     * @param cells
     *            the cells
     */
    private void writeCells(Iterator<String> cells) {
        int columnPosition = 1;
        try {
            while (cells.hasNext()) {
                String cell = cells.next();
                String nullSafeCell = cell != null ? cell : "";
                String headerValue = header != null ? header.getName(columnPosition) : "";
                LOG.debug("Writing cell value [{}] in column position [{}], header value is [{}].", nullSafeCell,
                        columnPosition, headerValue);
                if (columnPosition != 1) {
                    writeSeparator();
                }
                if (rowInstructions.getQuotingEnabled()) {
                    writeQuotedCell(nullSafeCell);
                } else {
                    writeCell(nullSafeCell);
                }
                columnPosition++;
            }
            writeEOL();
        } catch (IOException e) {
            LOG.trace("", e);
            throw new CsvException(new GeneralError("Error in writing to the writer: " + e.getMessage()));
        }
    }

    /**
     * Write EOL.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeEOL() throws IOException {
        writer.write(rowInstructions.getEndOfLine());
    }

    /**
     * Write separator.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeSeparator() throws IOException {
        writer.write(rowInstructions.getSeparator());
    }

    /**
     * Write quoted cell.
     *
     * @param cell
     *            the cell
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeQuotedCell(String cell) throws IOException {
        writer.write(rowInstructions.getQuote());
        String searchString = Character.toString(rowInstructions.getQuote());
        String replaceString = new String(new char[] { rowInstructions.getEscape(), rowInstructions.getQuote() });
        String replacedString = cell.replace(searchString, replaceString);
        writeCell(replacedString);
        writer.write(rowInstructions.getQuote());
    }

    /**
     * Write cell.
     *
     * @param cell
     *            the cell
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeCell(String cell) throws IOException {
        writer.write(cell);
    }

    /**
     * Convert to line.
     *
     * @param cells
     *            the cells
     *
     * @return the line with info
     */
    private LineWithInfo convertToLine(String[] cells) {
        LineWithInfo line = new LineWithInfo();
        for (String cell : cells) {
            line.addCell(cell);
        }
        return line;
    }

}
