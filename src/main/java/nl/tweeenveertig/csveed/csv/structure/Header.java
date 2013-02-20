package nl.tweeenveertig.csveed.csv.structure;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Header implements Iterable<String> {

    private int numberOfColumns;

    private Line header;
    private Map<Integer, String> indexToName = new TreeMap<Integer, String>();
    private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();

    public Header(Line row) {
        this.header = row;
        this.numberOfColumns = header.size();
        int indexColumn = 0;
        for (String headerCell : header) {
            this.indexToName.put(indexColumn, headerCell);
            this.nameToIndex.put(headerCell, indexColumn);
            indexColumn++;
        }
    }

    public String getName(Integer indexColumn) {
        return this.indexToName.get(indexColumn);
    }

    public Integer getIndex(String columnName) {
        return this.nameToIndex.get(columnName);
    }

    public boolean checkLine(Line line) {
        return line.size() == this.numberOfColumns;
    }

    public Iterator<String> iterator() {
        return header.iterator();
    }

}
