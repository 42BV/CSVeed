package nl.tweeenveertig.csveed.api;

import java.io.Reader;
import java.util.List;

/**
* <p>
*     The BeanReader is responsible for reading CSV rows and converting those into beans. There are two
*     ways to initialize a {@link nl.tweeenveertig.csveed.bean.BeanReaderImpl}:
* </p>
*
* <ul>
*     <li>Point it to class. The {@link nl.tweeenveertig.csveed.annotations} in the class are read, as is the order
*         of the properties within the class.</li>
*     <li>Roll your own. Pass a {@link nl.tweeenveertig.csveed.api.BeanReaderInstructions} implementation with your
*         own configuration settings</li>
* </ul>
*
* @param <T> the bean class into which the rows are converted
* @author Robert Bor
*/
public interface BeanReader<T> {

    /**
    * Reads all rows from the file and return these as beans.
    * @param reader Reader from which the beans are read
    * @return all beans read from the Reader
    */
    public List<T> read(Reader reader);

    /**
    * Reads a single row and returns this as a bean. The LineReader will keep track of its state.
    * @param reader Reader from which the bean is read
    * @return Bean read from the Reader
    */
    public T readLine(Reader reader);

    /**
    * Returns the line from which the bean was read. Note that a line is seen as a legitimate CSV row, not
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
