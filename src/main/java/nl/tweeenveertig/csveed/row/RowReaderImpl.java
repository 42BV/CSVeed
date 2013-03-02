package nl.tweeenveertig.csveed.row;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.report.RowError;
import nl.tweeenveertig.csveed.token.ParseException;
import nl.tweeenveertig.csveed.token.ParseStateMachine;

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

    private RowReaderInstructionsImpl lineReaderInstructions;

    private int maxNumberOfColumns = -1;

    private HeaderImpl header;

    private Reader reader;

    public RowReaderImpl(Reader reader) {
        this(reader, new RowReaderInstructionsImpl());
    }

    public RowReaderImpl(Reader reader, RowReaderInstructions instructionsInterface) {
        this.reader = reader;
        this.lineReaderInstructions = (RowReaderInstructionsImpl) instructionsInterface;
        stateMachine.setSymbolMapping(lineReaderInstructions.getSymbolMapping());
    }

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

    protected HeaderImpl getHeader() {
        return header == null && lineReaderInstructions.isUseHeader() ? readHeader() : header;
    }

    public HeaderImpl readHeader() {
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
            maxNumberOfColumns = unmappedLine.size();
        } else {
            if (unmappedLine.size() != maxNumberOfColumns) {
                throw new CsvException(new RowError(
                        "The expected number of columns is "+ maxNumberOfColumns +", whereas it is supposed to be "+unmappedLine.size(),
                        unmappedLine.reportOnEndOfLine(), getCurrentLine()
                ));
            }
        }
    }

    public boolean isFinished() {
        return stateMachine.isFinished();
    }

    protected void logSettings() {
        lineReaderInstructions.logSettings();
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
            line = stateMachine.ignoreLine() && lineReaderInstructions.isSkipEmptyLines() ? null : line;
        }
        return line;
    }

    public RowReaderInstructions getLineReaderInstructions() {
        return this.lineReaderInstructions;
    }

}
