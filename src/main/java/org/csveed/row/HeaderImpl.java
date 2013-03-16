package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;
import org.csveed.token.ParseStateMachine;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class HeaderImpl implements Header {

    private Line header;
    private Map<Integer, String> indexToName = new TreeMap<Integer, String>();
    private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();

    public HeaderImpl(Line row) {
        this.header = row;
        int indexColumn = ParseStateMachine.FIRST_COLUMN_INDEX;
        for (String headerCell : header) {
            this.indexToName.put(indexColumn, headerCell);
            this.nameToIndex.put(headerCell, indexColumn);
            indexColumn++;
        }
    }

    public int size() {
        return header.size();
    }

    public String getName(Integer indexColumn) {
        if (indexColumn == 0) {
            throw new CsvException(new GeneralError("Column index cannot be set at 0. Column indexes are 1-based"));
        }
        return this.indexToName.get(indexColumn);
    }

    public Integer getIndex(String columnName) {
        return this.nameToIndex.get(columnName);
    }

    public Iterator<String> iterator() {
        return header.iterator();
    }

    public RowReport reportOnEndOfLine() {
        return header.reportOnEndOfLine();
    }

}
