package nl.tweeenveertig.csveed.row;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.token.ParseException;
import nl.tweeenveertig.csveed.token.ParseStateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final Logger LOG = LoggerFactory.getLogger(RowReaderImpl.class);

    private ParseStateMachine stateMachine = new ParseStateMachine();

    private LineReaderInstructionsImpl lineReaderInstructions;

    private int numberOfColumns = -1;

    private HeaderImpl header;

    private Reader reader;

    public RowReaderImpl(Reader reader) {
        this(reader, new LineReaderInstructionsImpl());
    }

    public RowReaderImpl(Reader reader, LineReaderInstructions instructionsInterface) {
        this.reader = reader;
        this.lineReaderInstructions = (LineReaderInstructionsImpl) instructionsInterface;
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
        if (numberOfColumns == -1) {
            numberOfColumns = unmappedLine.size();
        } else {
            if (unmappedLine.size() != numberOfColumns) {
                String message = "The expected number of columns is "+numberOfColumns+", whereas it is supposed to be "+unmappedLine.size();
                LOG.error(message);
                for (String line : unmappedLine.reportOnEndOfLine().getPrintableLines()) {
                    LOG.error(getCurrentLine()+": "+line);
                }
                throw new CsvException(message, null, unmappedLine.reportOnEndOfLine(), getCurrentLine());
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
                    LOG.error(e.getMessage());
                    throw new CsvException(e.getMessage(), e, line.reportOnEndOfLine(), getCurrentLine());
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

    public LineReaderInstructions getLineReaderInstructions() {
        return this.lineReaderInstructions;
    }

}
