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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.csveed.common.Column;
import org.csveed.report.RowReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LineWithInfo.
 */
public class LineWithInfo implements Line {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(LineWithInfo.class);

    /** The cells. */
    private List<String> cells = new ArrayList<>();

    /** The original line. */
    private StringBuilder originalLine = new StringBuilder();

    /** The cell positions. */
    private Map<Column, CellPositionInRow> cellPositions = new TreeMap<>();

    /** The print length. */
    private int printLength;

    /** The current column. */
    private Column currentColumn = new Column();

    /**
     * Adds the cell.
     *
     * @param cell
     *            the cell
     */
    public void addCell(String cell) {
        this.cells.add(cell);
        if (cell == null || "".equals(cell)) {
            markStartOfColumn();
        }
        markEndOfColumn();
    }

    @Override
    public Iterator<String> iterator() {
        return cells.iterator();
    }

    /**
     * Mark start of column.
     */
    public void markStartOfColumn() {
        LOG.debug("Start of column: {}", printLength);
        getCellPosition(currentColumn).setStart(printLength);
    }

    /**
     * Mark end of column.
     */
    protected void markEndOfColumn() {
        LOG.debug("End of column: {}", printLength);
        getCellPosition(currentColumn).setEnd(printLength);
        currentColumn = currentColumn.nextColumn();
    }

    /**
     * Gets the cell position.
     *
     * @param column
     *            the column
     *
     * @return the cell position
     */
    protected CellPositionInRow getCellPosition(Column column) {
        CellPositionInRow cellPosition = cellPositions.get(column);
        if (cellPosition == null) {
            cellPosition = new CellPositionInRow();
            cellPositions.put(column, cellPosition);
        }
        return cellPosition;
    }

    /**
     * Adds the character.
     *
     * @param symbol
     *            the symbol
     */
    public void addCharacter(int symbol) {
        String printableChar = convertToPrintable(symbol);
        originalLine.append(printableChar);
        printLength += printableChar.length();
    }

    /**
     * Convert to printable.
     *
     * @param symbol
     *            the symbol
     *
     * @return the string
     */
    public String convertToPrintable(int symbol) {
        switch (symbol) {
            case -1:
                return "[EOF]";
            case '\b':
                return "\\b";
            case '\f':
                return "\\f";
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            case '\t':
                return "\\t";
            default:
                return Character.toString((char) symbol);
        }
    }

    @Override
    public int size() {
        return this.cells.size();
    }

    @Override
    public String get(int index) {
        return this.cells.get(index);
    }

    @Override
    public RowReport reportOnColumn(Column column) {
        CellPositionInRow cellPosition = cellPositions.get(column);
        if (cellPosition == null) {
            return null;
        }
        return new RowReport(originalLine.toString(), cellPosition.getStart(), cellPosition.getEnd());
    }

    @Override
    public RowReport reportOnEndOfLine() {
        return new RowReport(originalLine.toString(), printLength, printLength);
    }

}
