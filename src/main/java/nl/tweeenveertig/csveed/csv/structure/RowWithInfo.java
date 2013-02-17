package nl.tweeenveertig.csveed.csv.structure;

import java.util.*;

public class RowWithInfo implements Row {

    public static final int MAX_REPORT_SIZE = 80;

    private List<String> cells = new ArrayList<String>();

    private StringBuilder originalLine = new StringBuilder();

    private Map<Integer, CellPositionInRow> cellPositions = new TreeMap<Integer, CellPositionInRow>();

    private int printLength = 0;

    private int currentColumn = 0;

    public void addCell(String cell) {
        this.cells.add(cell);
        markEndOfColumn();
    }

    public Iterator<String> iterator() {
        return cells.iterator();
    }

    public void markStartOfColumn() {
        getCellPosition(currentColumn).setStart(printLength);
    }

    protected void markEndOfColumn() {
        getCellPosition(currentColumn++).setEnd(printLength);
    }

    protected CellPositionInRow getCellPosition(int columnIndex) {
        CellPositionInRow cellPosition = cellPositions.get(columnIndex);
        if (cellPosition == null) {
            cellPosition = new CellPositionInRow();
            cellPositions.put(columnIndex, cellPosition);
        }
        return cellPosition;
    }

    public void addCharacter(char character) {
        String printableChar = convertToPrintable(character);
        originalLine.append(printableChar);
        printLength += printableChar.length();
    }

    public String convertToPrintable(char character) {
        switch (character) {
            case '\b' : return "\\b";
            case '\f' : return "\\f";
            case '\n' : return "\\n";
            case '\r' : return "\\r";
            case '\t' : return "\\t";
            default: return Character.toString(character);
        }
    }

    public int size() {
        return this.cells.size();
    }

    public String get(int index) {
        return this.cells.get(index);
    }

    public RowReport reportOnColumn(int columnIndex) {
        CellPositionInRow cellPosition = getCellPosition(columnIndex);
        if (cellPosition == null) {
            return null;
        }
        return new RowReport(originalLine.toString(), cellPosition.getStart(), cellPosition.getEnd());
    }

    public RowReport reportOnEndOfLine() {
        return new RowReport(originalLine.toString(), printLength, printLength);
    }

}
