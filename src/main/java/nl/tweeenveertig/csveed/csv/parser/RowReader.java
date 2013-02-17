package nl.tweeenveertig.csveed.csv.parser;

import nl.tweeenveertig.csveed.csv.structure.Row;
import nl.tweeenveertig.csveed.csv.structure.RowWithInfo;
import nl.tweeenveertig.csveed.report.CsvException;
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
public class RowReader {

    public static final Logger LOG = LoggerFactory.getLogger(RowReader.class);

    private ParseStateMachine stateMachine = new ParseStateMachine();

    private int currentLine = -1; // Haven't started yet

    private int startLine = 0;

    private int headerLine = 0;

    public List<Row> readAllLines(Reader reader) {
        List<Row> allLines = new ArrayList<Row>();
        while (!stateMachine.isFinished()) {
            Row row = readLine(reader);
            if (row != null && row.size() > 0) {
                allLines.add(row);
            }
        }
        return allLines;
    }

    public boolean isFinished() {
        return stateMachine.isFinished();
    }

    public Row readLine(Reader reader) {
        RowWithInfo row = new RowWithInfo();
        this.currentLine++;

        if (isBeforeStartLine()) {
            skipToStartLine(reader);
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
                throw new CsvException(e.getMessage(), row.reportOnEndOfLine());
            }
            if (stateMachine.isTokenStart()) {
                row.markStartOfColumn();
            }
            if (token != null) {
                row.addCell(token);
            }
            row.addCharacter(symbol);
        }
        row = stateMachine.isEmptyLine() ? null : row;
        stateMachine.newLine();
        return row;
    }

    private boolean isBeforeStartLine() {
        return getCurrentLine() < this.startLine;
    }

    private void skipToStartLine(Reader reader) {
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

    public int getCurrentLine() {
        return this.currentLine;
    }

    public void setSymbolMapping(SymbolMapping symbolMapping) {
        this.stateMachine.setSymbolMapping(symbolMapping);
    }

    public SymbolMapping getSymbolMapping() {
        return this.stateMachine.getSymbolMapping();
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setHeaderLine(int headerLine) {
        this.headerLine = headerLine;
    }

    public boolean isHeaderLine() {
        return getCurrentLine() == this.headerLine;
    }

}
