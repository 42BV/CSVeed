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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.csveed.api.Header;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;

public class HeaderImpl implements Header {

    private Line header;
    private Map<Column, String> indexToName = new HashMap<>();
    private Map<String, Column> nameToIndex = new HashMap<>();

    public HeaderImpl(Line row) {
        this.header = row;
        Column currentColumn = new Column();
        for (String headerCell : header) {
            this.indexToName.put(currentColumn, headerCell);
            this.nameToIndex.put(headerCell.toLowerCase(Locale.getDefault()), currentColumn);
            currentColumn = currentColumn.nextColumn();
        }
    }

    @Override
    public int size() {
        return header.size();
    }

    @Override
    public String getName(int columnIndex) {
        Column column = new Column(columnIndex);
        String name = this.indexToName.get(column);
        if (name == null) {
            throw new CsvException(new GeneralError("No column name found for index " + column.getColumnIndex()));
        }
        return name;
    }

    @Override
    public int getIndex(String columnName) {
        Column column = this.nameToIndex.get(columnName.toLowerCase(Locale.getDefault()));
        if (column == null) {
            throw new CsvException(new GeneralError("No column index found for name " + columnName));
        }
        return column.getColumnIndex();
    }

    @Override
    public Iterator<String> iterator() {
        return header.iterator();
    }

    @Override
    public RowReport reportOnEndOfLine() {
        return header.reportOnEndOfLine();
    }

}
