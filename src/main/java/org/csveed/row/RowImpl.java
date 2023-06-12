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

import java.util.Iterator;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;

/**
 * The Class RowImpl.
 */
public class RowImpl implements Row {

    /** The line. */
    private Line line;

    /** The header. */
    private Header header;

    /**
     * Instantiates a new row impl.
     *
     * @param line
     *            the line
     * @param header
     *            the header
     */
    public RowImpl(Line line, Header header) {
        this.line = line;
        this.header = header;
    }

    @Override
    public Header getHeader() {
        if (this.header == null) {
            throw new CsvException(new GeneralError(
                    "No header has been found for this file. Set @CsvFile#useHeaders to read the header"));
        }
        return this.header;
    }

    @Override
    public RowReport reportOnEndOfLine() {
        return line.reportOnEndOfLine();
    }

    @Override
    public RowReport reportOnColumn(int columnIndex) {
        return line.reportOnColumn(new Column(columnIndex));
    }

    @Override
    public String get(String columnName) {
        return line.get(header.getIndex(columnName) - 1);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getHeader().getName(columnIndex);
    }

    @Override
    public boolean hasHeader() {
        return header != null;
    }

    @Override
    public int size() {
        return line.size();
    }

    @Override
    public String get(int columnIndex) {
        return line.get(columnIndex - 1);
    }

    /**
     * Returns an iterator over the individual cells of a Row
     *
     * @return iterator over the cells in String format
     */
    @Override
    public Iterator<String> iterator() {
        return line.iterator();
    }

}
