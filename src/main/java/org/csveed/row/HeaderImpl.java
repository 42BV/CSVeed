package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.common.Column;
import org.csveed.common.ColumnIndex;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HeaderImpl implements Header {

    private Line header;
    private Map<Column, String> indexToName = new HashMap<Column, String>();
    private Map<String, Column> nameToIndex = new HashMap<String, Column>();

    public HeaderImpl(Line row) {
        this.header = row;
        Column currentColumn = new ColumnIndex();
        for (String headerCell : header) {
            this.indexToName.put(currentColumn, headerCell);
            this.nameToIndex.put(headerCell, currentColumn);
            currentColumn = currentColumn.nextColumn();
        }
    }

    public int size() {
        return header.size();
    }

    public String getName(int columnIndex) {
        Column column = new ColumnIndex(columnIndex);
        String name = this.indexToName.get(column);
        if (name == null) {
            throw new CsvException(new GeneralError("No column name found for index "+column.getColumnIndex()));
        }
        return name;
    }

    public int getIndex(String columnName) {
        Column column = this.nameToIndex.get(columnName);
        if (column == null) {
            throw new CsvException(new GeneralError("No column index found for name "+columnName));
        }
        return column.getColumnIndex();
    }

    public Iterator<String> iterator() {
        return header.iterator();
    }

    public RowReport reportOnEndOfLine() {
        return header.reportOnEndOfLine();
    }

}
