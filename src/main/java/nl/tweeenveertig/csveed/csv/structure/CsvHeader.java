package nl.tweeenveertig.csveed.csv.structure;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CsvHeader {

    private int numberOfColumns;

    private Row header;
    private Map<Integer, String> indexToName = new TreeMap<Integer, String>();
    private Map<String, Integer> nameToIndex = new TreeMap<String, Integer>();

    public CsvHeader(Row row) {
        this.header = row;
        this.numberOfColumns = header.size();
        int indexColumn = 0;
        for (String headerCell : header) {
            this.indexToName.put(indexColumn, headerCell);
            this.nameToIndex.put(headerCell, indexColumn);
            indexColumn++;
        }
    }

    public Row getHeader() {
        return this.header;
    }

    public String getName(Integer indexColumn) {
        return this.indexToName.get(indexColumn);
    }

    public Integer getIndex(String name) {
        return this.nameToIndex.get(name);
    }

    public boolean checkLine(List<String> line) {
        return line.size() == this.numberOfColumns;
    }

}
