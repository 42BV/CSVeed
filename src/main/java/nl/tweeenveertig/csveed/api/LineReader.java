package nl.tweeenveertig.csveed.api;

import java.util.List;

/**
* LineReaders reads rows from the CSV file and returns those all at once, or one by one if desired.
* @author Robert Bor
*/
public interface LineReader {

    /**
    * Reads all rows from the file and returns them as a List. After this, the LineReader will be finished
    * @return all Rows read from the Reader
    */
    public List<Row> read();

    /**
    * Reads a single row from the file and returns this. The LineReader will keep track of its state.
    * @return Row read from the Reader
    */
    public Row readLine();

    /**
    * Returns the line from which the row was read. Note that a line is seen as a legitimate CSV row, not
    * necessarily a printable line (unless multi-lines are used, these values are the same).
    * @return current line number
    */
    public int getCurrentLine();

    /**
    * States whether the Reader is done with the file
    * @return true if file is finished
    */
    public boolean isFinished();

}
