package org.csveed.bean;

import org.csveed.api.Header;
import org.csveed.row.RowReader;

import java.util.List;

/**
* <p>
*     The BeanReader is responsible for reading CSV rows and converting those into beans. There are two
*     ways to initialize a {@link org.csveed.bean.BeanReaderImpl}:
* </p>
*
* <ul>
*     <li>Point it to class. The {@link org.csveed.annotations} in the class are read, as is the order
*         of the properties within the class.</li>
*     <li>Roll your own. Pass a {@link BeanInstructions} implementation with your
*         own configuration settings</li>
* </ul>
*
* @param <T> the bean class into which the rows are converted
* @author Robert Bor
*/
public interface BeanReader<T> {

    /**
    * Reads all rows from the file and return these as beans.
    * @return all beans read from the Reader
    */
    public List<T> readBeans();

    /**
    * Reads a single row and returns this as a bean. The RowReader will keep track of its state.
    * @return Bean read from the Reader
    */
    public T readBean();

    /**
    * Returns the first readable line of the CSV file as header, regardless if useHeader==true.
    * @return header
    */
    public Header readHeader();

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

    /**
    * Returns the underlying line reader for the bean reader
    * @return the underlying line reader
    */
    public RowReader getRowReader();

    /**
    * The set of instructions for dealing with beans
    * @return bean instructions
    */
    public BeanInstructions getBeanInstructions();

}
