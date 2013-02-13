package nl.tweeenveertig.csveed.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
* Builds up a List of cells (String) per read line.
* @author Robert Bor
*/
public class LineReader {

    private ParseStateMachine stateMachine = new ParseStateMachine();

    public List<List<String>> readAllLines(Reader reader) throws IOException {
        List<List<String>> allLines = new ArrayList<List<String>>();
        while (!stateMachine.isFinished()) {
            List<String> cells = readLine(reader);
            if (cells != null && cells.size() > 0) {
                allLines.add(cells);
            }
        }
        return allLines;
    }

    public List<String> readLine(Reader reader) throws IOException {
        List<String> cells = new ArrayList<String>();

        while (!stateMachine.isLineFinished()) {
            String token = stateMachine.offerSymbol(reader.read());
            if (token != null) {
                cells.add(token);
            }
        }
        cells = stateMachine.isEmptyLine() ? null : cells;
        stateMachine.newLine();
        return cells;
    }

    public void setStateMachine(ParseStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

}
