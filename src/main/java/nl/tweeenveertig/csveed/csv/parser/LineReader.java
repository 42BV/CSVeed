package nl.tweeenveertig.csveed.csv.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
* Builds up a List of cells (String) per read line. Note that this class is stateful, so it
* can support a per-line parse approach as well.
* @author Robert Bor
*/
public class LineReader {

    private ParseStateMachine stateMachine = new ParseStateMachine();

    private int currentLine = -1; // Haven't started yet

    private int startLine = 0;

    private int headerLine = 0;

    public List<List<String>> readAllLines(Reader reader) {
        List<List<String>> allLines = new ArrayList<List<String>>();
        while (!stateMachine.isFinished()) {
            List<String> cells = readLine(reader);
            if (cells != null && cells.size() > 0) {
                allLines.add(cells);
            }
        }
        return allLines;
    }

    public boolean isFinished() {
        return stateMachine.isFinished();
    }

    public List<String> readLine(Reader reader) {
        List<String> cells = new ArrayList<String>();
        this.currentLine++;

        if (isBeforeStartLine()) {
            skipToStartLine(reader);
        }

        while (!stateMachine.isLineFinished()) {
            final String token;
            try {
                token = stateMachine.offerSymbol(reader.read());
            } catch (IOException err) {
                throw new RuntimeException(err);
            }
            if (token != null) {
                cells.add(token);
            }
        }
        cells = stateMachine.isEmptyLine() ? null : cells;
        stateMachine.newLine();
        return cells;
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
