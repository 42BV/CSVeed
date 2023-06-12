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

import java.util.Collection;

import org.csveed.api.Header;
import org.csveed.api.Row;

public interface RowWriter {

    /**
     * Writes multiple rows with cells to the table.
     *
     * @param rows
     *            two-dimensional string array with rows and cells within
     */
    void writeRows(String[][] rows);

    /**
     * Writes multiples rows to the table.
     *
     * @param rows
     *            collection of rows
     */
    void writeRows(Collection<Row> rows);

    /**
     * Writes the cells of a table row as an individual row.
     *
     * @param cells
     *            the individual cells of the row
     *
     * @return the row just written
     */
    Row writeRow(String[] cells);

    /**
     * Writes a single row to the Writer.
     *
     * @param row
     *            row to write to the Writer
     */
    void writeRow(Row row);

    /**
     * Creates and sets the header of the table.
     *
     * @param headerNames
     *            the individual cells of the header row
     *
     * @return the Header, created from the header names
     */
    Header writeHeader(String[] headerNames);

    /**
     * Sets the header of the table.
     *
     * @param header
     *            the header row
     */
    void writeHeader(Header header);

    /**
     * The set of instructions for dealing with rows.
     *
     * @return row instructions
     */
    RowInstructions getRowInstructions();

}
