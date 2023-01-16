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
package org.csveed.api;

import org.csveed.report.RowReport;

/**
 * A Row is a line of content read from the CSV file. Note that a Row is never a Header. Rows can be iterated which
 * yields the individual cells as Strings.
 *
 * @author Robert Bor
 */
public interface Row extends Iterable<String> {

    /**
     * The number of columns in the Row.
     *
     * @return number of columns
     */
    int size();

    /**
     * Gets the content of the cell on the basis of its cell position within the Row. Counting is 0-based.
     *
     * @param columnIndex
     *            the position of the cell within the Row
     *
     * @return the content of the cell
     */
    String get(int columnIndex);

    /**
     * Gets the content of the cell in the column named columnName. This method depends on the availability of a Header,
     * or will otherwise throw an exception
     *
     * @param columnName
     *            the cell of the Row in the same column as the header with name columnName
     *
     * @return the content of the cell
     */
    String get(String columnName);

    /**
     * Gets the column name belonging to the cell. This method depends on the availability of a Header, or will
     * otherwise throw an exception
     *
     * @param columnIndex
     *            the position of the header cell within the Header line
     *
     * @return the name of the column
     */
    String getColumnName(int columnIndex);

    /**
     * Returns true if a Header has been set for this Row.
     *
     * @return true if Header has been found
     */
    boolean hasHeader();

    /**
     * Returns the Headers and throws an exception if it does not exist. Use hasHeader() if you want to prevent the
     * throwing of an exception when a Header can lack
     *
     * @return the Header for the Row
     */
    Header getHeader();

    /**
     * Generates a report on the Row with focus on the end of the row. This is internally used when there is a syntax
     * error in the line. It could also be used when there is an error that can not be traced back to a single cell.
     *
     * @return report on the row with focus on the end of the row
     */
    RowReport reportOnEndOfLine();

    /**
     * Generates a report on the Row with focus on a particular cell. The report can return lines for web consumption
     * (assisting highlighting) or monospaced font printing with dual lines, the first holding the value of the line,
     * the second showing where the error occurred.
     *
     * @param columnIndex
     *            the index of the column to focus the report on
     *
     * @return report on the row with focus on a particular cell
     */
    RowReport reportOnColumn(int columnIndex);

}
