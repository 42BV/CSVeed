package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;
import org.csveed.token.ParseStateMachine;
import org.csveed.util.ExcelColumn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class HeaderImpl implements Header {

    private Line header;
    private Map<ExcelColumn, String> indexToName = new HashMap<ExcelColumn, String>();
    private Map<String, ExcelColumn> nameToIndex = new HashMap<String, ExcelColumn>();

    public HeaderImpl(Line row) {
        this.header = row;
        ExcelColumn currentColumn = new ExcelColumn();
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
        ExcelColumn column = new ExcelColumn(columnIndex);
        String name = this.indexToName.get(column);
        if (name == null) {
            throw new CsvException(new GeneralError("No column name found for index "+column.getColumnIndex()));
        }
        return name;
    }

    public int getIndex(String columnName) {
        ExcelColumn column = this.nameToIndex.get(columnName);
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
