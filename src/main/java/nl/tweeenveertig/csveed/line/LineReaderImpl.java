package nl.tweeenveertig.csveed.line;

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
* Builds up a List of cells (String) per read line. Note that this class is stateful, so it
* can support a per-line parse approach as well.
* @author Robert Bor
*/
public class LineReaderImpl implements LineReader {

    public static final Logger LOG = LoggerFactory.getLogger(LineReaderImpl.class);

    private ParseStateMachine stateMachine = new ParseStateMachine();

    private int currentLine = -1; // Haven't started yet

    private int startLine = 0;

    private int headerLine = 0;

    private int numberOfColumns = -1;

    private Header header;

    private Reader reader;

    public LineReaderImpl(Reader reader) {
        this(reader, new LineReaderInstructionsImpl());
    }

    public LineReaderImpl(Reader reader, LineReaderInstructions instructionsInterface) {
        this.reader = reader;
        init((LineReaderInstructionsImpl) instructionsInterface);
    }

    private void init(LineReaderInstructionsImpl instructions) {
        this.stateMachine.setSymbolMapping(instructions.getSymbolMapping());
        this.stateMachine.getSymbolMapping().logSettings();
        this.startLine = instructions.getStartRow();
        LOG.info("- CSV config / start line: "+instructions.getStartRow());

        if (instructions.isUseHeader()) {
            this.headerLine = instructions.getStartRow();
            LOG.info("- CSV config / has header line? yes");
        } else {
            this.headerLine = -1;
            LOG.info("- CSV config / has header line? no");
        }
    }

    public List<Row> readLines() {
        List<Row> allRows = new ArrayList<Row>();
        while (!isFinished()) {
            Row row = readLine();
            if (row != null && row.size() > 0) {
                allRows.add(row);
            }
        }
        return allRows;
    }

    public Row readLine() {
        Line unmappedLine = readBareLine();
        if (unmappedLine == null) {
            return null;
        }
        checkNumberOfColumns(unmappedLine);
        if (isHeaderLine()) {
            header = new Header(unmappedLine);
            unmappedLine = readBareLine();
        }
        return new RowImpl(unmappedLine, header);
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

    public int getCurrentLine() {
        return this.currentLine;
    }

    protected Line readBareLine() {
        LineWithInfo line = new LineWithInfo();
        this.currentLine++;

        if (isBeforeStartLine()) {
            skipToStartLine();
        }

        while (!stateMachine.isLineFinished()) {
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
        }
        line = stateMachine.isEmptyLine() ? null : line;
        stateMachine.newLine();
        return line;
    }

    private boolean isBeforeStartLine() {
        return getCurrentLine() < this.startLine;
    }

    private void skipToStartLine() {
        try {
            int symbol = reader.read();
            while (isBeforeStartLine() && symbol != -1) {
                if (stateMachine.isEol(symbol)) {
                    this.currentLine++;
                    if (!isBeforeStartLine()) {
                        break;
                    }
                }
                symbol = reader.read();
            }
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    private boolean isHeaderLine() {
        return getCurrentLine() == this.headerLine;
    }

}
