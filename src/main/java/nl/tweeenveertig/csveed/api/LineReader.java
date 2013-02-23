package nl.tweeenveertig.csveed.api;

import java.io.Reader;
import java.util.List;

public interface LineReader {

    public List<Row> read(Reader reader);

    public Row readLine(Reader reader);

    public int getCurrentLine();

    public boolean isFinished();

}
