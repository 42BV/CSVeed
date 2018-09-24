package org.csveed.row;

import org.csveed.common.Column;
import org.csveed.report.RowReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LineWithInfo implements Line {

    public static final Logger LOG = LoggerFactory.getLogger(LineWithInfo.class);

    private List<String> cells = new ArrayList<>();

    private StringBuilder originalLine = new StringBuilder();

    private Map<Column, CellPositionInRow> cellPositions = new TreeMap<>();

    private int printLength;

    private Column currentColumn = new Column();

    public void addCell(String cell) {
        this.cells.add(cell);
        if (cell == null || "".equals(cell)) {
            markStartOfColumn();
        }
        markEndOfColumn();
    }

    @Override
    public Iterator<String> iterator() {
        return cells.iterator();
    }

    public void markStartOfColumn() {
        LOG.debug("Start of column: {}", printLength);
        getCellPosition(currentColumn).setStart(printLength);
    }

    protected void markEndOfColumn() {
        LOG.debug("End of column: {}", printLength);
        getCellPosition(currentColumn).setEnd(printLength);
        currentColumn = currentColumn.nextColumn();
    }

    protected CellPositionInRow getCellPosition(Column column) {
        CellPositionInRow cellPosition = cellPositions.get(column);
        if (cellPosition == null) {
            cellPosition = new CellPositionInRow();
            cellPositions.put(column, cellPosition);
        }
        return cellPosition;
    }

    public void addCharacter(int symbol) {
        String printableChar = convertToPrintable(symbol);
        originalLine.append(printableChar);
        printLength += printableChar.length();
    }

    public String convertToPrintable(int symbol) {
        if (symbol == -1) return "[EOF]";
        else if (symbol == '\b') return "\\b";
        else if (symbol == '\f') return "\\f";
        else if (symbol == '\n') return "\\n";
        else if (symbol == '\r') return "\\r";
        else if (symbol == '\t') return "\\t";
        else return Character.toString((char)symbol);
    }

    @Override
    public int size() {
        return this.cells.size();
    }

    @Override
    public String get(int index) {
        return this.cells.get(index);
    }

    @Override
    public RowReport reportOnColumn(Column column) {
        CellPositionInRow cellPosition = cellPositions.get(column);
        if (cellPosition == null) {
            return null;
        }
        return new RowReport(originalLine.toString(), cellPosition.getStart(), cellPosition.getEnd());
    }

    @Override
    public RowReport reportOnEndOfLine() {
        return new RowReport(originalLine.toString(), printLength, printLength);
    }

}
