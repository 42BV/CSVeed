package nl.tweeenveertig.csveed.csv.structure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RowWithInfo implements Row {

    public static final Logger LOG = LoggerFactory.getLogger(RowWithInfo.class);

    private List<String> cells = new ArrayList<String>();

    private StringBuilder originalLine = new StringBuilder();

    private Map<Integer, CellPositionInRow> cellPositions = new TreeMap<Integer, CellPositionInRow>();

    private int printLength = 0;

    private int currentColumn = 0;

    public void addCell(String cell) {
        this.cells.add(cell);
        if (cell == null || "".equals(cell)) {
            markStartOfColumn();
        }
        markEndOfColumn();
    }

    public Iterator<String> iterator() {
        return cells.iterator();
    }

    public void markStartOfColumn() {
        LOG.debug("Start of column: "+printLength);
        getCellPosition(currentColumn).setStart(printLength);
    }

    protected void markEndOfColumn() {
        LOG.debug("End of column: "+printLength);
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

    public void addCharacter(int symbol) {
        String printableChar = convertToPrintable(symbol);
        originalLine.append(printableChar);
        printLength += printableChar.length();
    }

    public String convertToPrintable(int symbol) {
        switch (symbol) {
            case -1 : return "[EOF]";
            case '\b' : return "\\b";
            case '\f' : return "\\f";
            case '\n' : return "\\n";
            case '\r' : return "\\r";
            case '\t' : return "\\t";
            default: return Character.toString((char)symbol);
        }
    }

    public int size() {
        return this.cells.size();
    }

    public String get(int index) {
        return this.cells.get(index);
    }

    public RowReport reportOnColumn(int columnIndex) {
        CellPositionInRow cellPosition = cellPositions.get(columnIndex);
        if (cellPosition == null) {
            return null;
        }
        return new RowReport(originalLine.toString(), cellPosition.getStart(), cellPosition.getEnd());
    }

    public RowReport reportOnEndOfLine() {
        return new RowReport(originalLine.toString(), printLength, printLength);
    }

}
