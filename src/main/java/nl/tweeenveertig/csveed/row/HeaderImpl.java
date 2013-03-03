package nl.tweeenveertig.csveed.row;

import nl.tweeenveertig.csveed.api.Header;
import nl.tweeenveertig.csveed.report.RowReport;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class HeaderImpl implements Header {

    private Line header;
    private Map<Integer, String> indexToName = new TreeMap<Integer, String>();
    private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();

    public HeaderImpl(Line row) {
        this.header = row;
        int indexColumn = 0;
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
