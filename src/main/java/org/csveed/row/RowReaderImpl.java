package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;
import org.csveed.token.ParseException;
import org.csveed.token.ParseStateMachine;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
* Builds up a List of cells (String) per read row. Note that this class is stateful, so it
* can support a per-row parse approach as well.
* @author Robert Bor
*/
public class RowReaderImpl implements RowReader {

    private ParseStateMachine stateMachine = new ParseStateMachine();

    private RowInstructionsImpl rowInstructions;

    private int maxNumberOfColumns = -1;

    private HeaderImpl header;

    private Reader reader;

    public RowReaderImpl(Reader reader) {
        this(reader, new RowInstructionsImpl());
    }

    public RowReaderImpl(Reader reader, RowInstructions instructionsInterface) {
        this.reader = reader;
        this.rowInstructions = (RowInstructionsImpl) instructionsInterface;
        stateMachine.setSymbolMapping(rowInstructions.getSymbolMapping());
    }

    @Override
    public List<Row> readRows() {
        List<Row> allRows = new ArrayList<Row>();
        while (!isFinished()) {
            Row row = readRow();
            if (row != null && row.size() > 0) {
                allRows.add(row);
            }
        }
        return allRows;
    }

    @Override
    public Row readRow() {
        getHeader();
        Line unmappedLine = readBareLine();
        if (unmappedLine == null) {
            return null;
        }
        checkNumberOfColumns(unmappedLine);
        return new RowImpl(unmappedLine, getHeader());
    }

    @Override
    public int getCurrentLine() {
        return this.stateMachine.getCurrentLine();
    }

    @Override
    public Header getHeader() {
        return header == null && rowInstructions.isUseHeader() ? readHeader() : header;
    }

    public int getMaxNumberOfColumns() {
        return this.maxNumberOfColumns;
    }

    @Override
    public Header readHeader() {
        if (header != null) {
            return header;
        }
        Line unmappedLine = readBareLine();
        if (unmappedLine == null) {
            return null;
        }
        return header = new HeaderImpl(unmappedLine);
    }

    private void checkNumberOfColumns(Line unmappedLine) {
        if (maxNumberOfColumns == -1) {
            maxNumberOfColumns = header == null ? unmappedLine.size() : header.size();
        }
        if (unmappedLine.size() != maxNumberOfColumns) {
            throw new CsvException(new RowError(
                    "The expected number of columns is " + maxNumberOfColumns + ", whereas it was " + unmappedLine.size(),
                    unmappedLine.reportOnEndOfLine(), getCurrentLine()
                    ));
        }
    }

    @Override
    public boolean isFinished() {
        return stateMachine.isFinished();
    }

    protected void logSettings() {
        rowInstructions.logSettings();
        this.stateMachine.getSymbolMapping().logSettings();
    }

    protected Line readBareLine() {
        logSettings();

        LineWithInfo line = null;
        while (line == null && !stateMachine.isFinished()) {
            line = new LineWithInfo();
            while (!stateMachine.isFinished()) {
                final String token;
                final int symbol;
                try {
                    symbol = reader.read();
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
                try {
                    token = stateMachine.offerSymbol(symbol);
                } catch (ParseException e) {
                    throw new CsvException(new RowError(e.getMessage(), line.reportOnEndOfLine(), getCurrentLine()));
                }
                if (stateMachine.isTrash()) {
                    continue;
                }
                if (stateMachine.isTokenStart()) {
                    line.markStartOfColumn();
                }
                if (token != null) {
                    line.addCell(token);
                }
                line.addCharacter(symbol);
                if (stateMachine.isLineFinished()) {
                    break;
                }
            }
            line = stateMachine.ignoreLine() && rowInstructions.isSkipEmptyLines() ? null : line;
        }
        return line;
    }

    @Override
    public RowInstructions getRowInstructions() {
        return this.rowInstructions;
    }

}
