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
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;
import org.csveed.token.ParseException;
import org.csveed.token.ParseStateMachine;

/**
 * Builds up a List of cells (String) per read row. Note that this class is stateful, so it can support a per-row parse
 * approach as well.
 */
public class RowReaderImpl implements RowReader {

    /** The state machine. */
    private ParseStateMachine stateMachine = new ParseStateMachine();

    /** The row instructions. */
    private RowInstructionsImpl rowInstructions;

    /** The max number of columns. */
    private int maxNumberOfColumns = -1;

    /** The header. */
    private HeaderImpl header;

    /** The reader. */
    private Reader reader;

    /**
     * Instantiates a new row reader impl.
     *
     * @param reader
     *            the reader
     */
    public RowReaderImpl(Reader reader) {
        this(reader, new RowInstructionsImpl());
    }

    /**
     * Instantiates a new row reader impl.
     *
     * @param reader
     *            the reader
     * @param instructionsInterface
     *            the instructions interface
     */
    public RowReaderImpl(Reader reader, RowInstructions instructionsInterface) {
        this.reader = reader;
        this.rowInstructions = (RowInstructionsImpl) instructionsInterface;
        stateMachine.setSymbolMapping(rowInstructions.getSymbolMapping());
    }

    @Override
    public List<Row> readRows() {
        List<Row> allRows = new ArrayList<>();
        while (!isFinished()) {
            Row row = readRow();
            if (row != null && row.size() > 0) {
                allRows.add(row);
            }
        }
        return allRows;
    }

    @Override
    public Row readRow() {
        getHeader();
        Line unmappedLine = readBareLine();
        if (unmappedLine == null) {
            return null;
        }
        checkNumberOfColumns(unmappedLine);
        return new RowImpl(unmappedLine, getHeader());
    }

    @Override
    public int getCurrentLine() {
        return this.stateMachine.getCurrentLine();
    }

    @Override
    public Header getHeader() {
        return header == null && rowInstructions.isUseHeader() ? readHeader() : header;
    }

    /**
     * Gets the max number of columns.
     *
     * @return the max number of columns
     */
    public int getMaxNumberOfColumns() {
        return this.maxNumberOfColumns;
    }

    @Override
    public Header readHeader() {
        if (header != null) {
            return header;
        }
        Line unmappedLine = readBareLine();
        if (unmappedLine == null) {
            return null;
        }
        header = new HeaderImpl(unmappedLine);
        return header;
    }

    /**
     * Check number of columns.
     *
     * @param unmappedLine
     *            the unmapped line
     */
    private void checkNumberOfColumns(Line unmappedLine) {
        if (maxNumberOfColumns == -1) {
            maxNumberOfColumns = header == null ? unmappedLine.size() : header.size();
        }
        if (unmappedLine.size() != maxNumberOfColumns) {
            throw new CsvException(new RowError("The expected number of columns is " + maxNumberOfColumns
                    + ", whereas it was " + unmappedLine.size(), unmappedLine.reportOnEndOfLine(), getCurrentLine()));
        }
    }

    @Override
    public boolean isFinished() {
        return stateMachine.isFinished();
    }

    /**
     * Log settings.
     */
    protected void logSettings() {
        rowInstructions.logSettings();
        this.stateMachine.getSymbolMapping().logSettings();
    }

    /**
     * Read bare line.
     *
     * @return the line
     */
    protected Line readBareLine() {
        logSettings();

        LineWithInfo line = null;
        while (line == null && !stateMachine.isFinished()) {
            line = new LineWithInfo();
            while (!stateMachine.isFinished()) {
                final String token;
                final int symbol;
                try {
                    symbol = reader.read();
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
                try {
                    token = stateMachine.offerSymbol(symbol);
                } catch (ParseException e) {
                    throw new CsvException(new RowError(e.getMessage(), line.reportOnEndOfLine(), getCurrentLine()));
                }
                if (stateMachine.isTrash()) {
                    continue;
                }
                if (stateMachine.isTokenStart()) {
                    line.markStartOfColumn();
                }
                if (token != null) {
                    line.addCell(token);
                }
                line.addCharacter(symbol);
                if (stateMachine.isLineFinished()) {
                    break;
                }
            }
            line = stateMachine.ignoreLine() && rowInstructions.isSkipEmptyLines() ? null : line;
        }
        return line;
    }

    @Override
    public RowInstructions getRowInstructions() {
        return this.rowInstructions;
    }

}
