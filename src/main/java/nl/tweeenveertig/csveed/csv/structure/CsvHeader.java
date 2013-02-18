package nl.tweeenveertig.csveed.csv.structure;

import java.util.Map;
import java.util.TreeMap;

public class CsvHeader {

    private int numberOfColumns;

    private Row header;
    private Map<Integer, String> indexToName = new TreeMap<Integer, String>();

    public CsvHeader(Row row) {
        this.header = row;
        this.numberOfColumns = header.size();
        int indexColumn = 0;
        for (String headerCell : header) {
            this.indexToName.put(indexColumn, headerCell);
            indexColumn++;
        }
    }

    public Row getHeader() {
        return this.header;
    }

    public String getName(Integer indexColumn) {
        return this.indexToName.get(indexColumn);
    }

    public boolean checkLine(Row line) {
        return line.size() == this.numberOfColumns;
    }

}
