package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.bean.AbstractMapper;

import java.beans.PropertyEditor;
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
*     <li>Roll your own. Pass a {@link nl.tweeenveertig.csveed.bean.BeanReaderInstructions} implementation with your
*         own configuration settings</li>
* </ul>
*
* @param <T> the bean class into which the rows are converted
* @author Robert Bor
*/
public interface CsvReader<T> {

    /**
    * Reads all rows from the file and return these as beans.
    * @return all beans read from the Reader
    */
    public List<T> readBeans();

    /**
    * Reads a single row and returns this as a bean. The LineReader will keep track of its state.
    * @return Bean read from the Reader
    */
    public T readBean();

    /**
    * Reads all rows from the file and returns them as a List. After this, the LineReader will be finished
    * @return all Rows read from the Reader
    */
    public List<Row> readLines();

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

    /**
    * Makes sure that the first readable line is interpreted as the header line. That line will not be read
    * as content. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#useHeader()}
    * is used. The default value for this setting is true. This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setUseHeader(boolean)}.
    * @param useHeader true if the header is interpreted and used
    * @return convenience for chaining
    */
    CsvReader<T> setUseHeader(boolean useHeader);

    /**
    * Sets the start row of the CSV file. If {@link #setUseHeader(boolean)} == true, this will be the header
    * row and the next ones are all content rows. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#startRow()} is used. The default value for this
    * setting is 0. This call is a facade for {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setStartRow(int)}.
    * @param startRow the first row to start reading, including the header row
    * @return convenience for chaining
    */
    CsvReader<T> setStartRow(int startRow);

    /**
    * Sets the character that will be interpreted as an escape symbol while within a quoted field. This
    * method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#escape()} is used. The
    * default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setEscape(char)}.
    * @param symbol the symbol to use for escaping characters within a quoted field
    * @return convenience for chaining
    */
    CsvReader<T> setEscape(char symbol);

    /**
    * Sets the character that will be interpreted as a quote symbol, signifying either the start or the
    * end of a quoted field. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#quote()}
    * is used. The default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setQuote(char)}.
    * @param symbol the symbol to use for indicating start/end of a quoted field
    * @return convenience for chaining
    */
    CsvReader<T> setQuote(char symbol);

    /**
    * Sets the character that will be interpreted as a separator between cells. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#separator()} is used. The default value for this
    * setting is a semi-colon (;). This call is a facade for {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setSeparator(char)}.
    * @param symbol the symbol to use as a separator between cells
    * @return convenience for chaining
    */
    CsvReader<T> setSeparator(char symbol);

    /**
    * Sets the characters (plural) that will be interpreted as end-of-line markers (unless within a quoted
    * field). This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#endOfLine()}
    * is used. The default values for this setting are \r and \n.  This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setEndOfLine(char[])}.
    * @param symbols the symbol to interpret as end-of-line markers (unless within a quoted field)
    * @return convenience for chaining
    */
    CsvReader<T> setEndOfLine(char[] symbols);

    /**
    * Determines which mapping strategy is to be employed for mapping cells to bean properties. This
    * method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#mappingStrategy()} is
    * used. The default mapping strategy is {@link nl.tweeenveertig.csveed.bean.ColumnIndexMapper}, which
    * looks at either the position of a property within the class or the custom index if
    * {@link nl.tweeenveertig.csveed.annotations.CsvCell#indexColumn()} or {@link #mapColumnIndexToProperty(int, String)}
    * has been set.
    * @param mapper the mapping strategy to employ for mapping cells to bean properties
    * @return convenience for chaining
    */
    CsvReader<T> setMapper(Class<? extends AbstractMapper> mapper);

    /**
    * Determines if the field is required. If so, the cell may not be empty and a
    * {@link nl.tweeenveertig.csveed.report.CsvException} will be thrown if this occurs. This method is called
    * whenever {@link nl.tweeenveertig.csveed.annotations.CsvCell#required()} is used. The default for a property
    * is false.
    * @param propertyName property for which the requirement applies
    * @param required whether the cell must be not-null
    * @return convenience for chaining
    */
    CsvReader<T> setRequired(String propertyName, boolean required);

    /**
    * Sets a custom {@link java.beans.PropertyEditor} for the property. This PropertyEditor is called to convert the
    * text to the type of the property and set it on the bean. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvConverter#converter()} is used. The default for a property
    * is based on the wonderful set of PropertyEditors that Spring offers, which is all basics and some extras
    * as well.
    * @param propertyName property to which the converter must be applied
    * @param converter PropertyEditor to apply to the property
    * @return convenience for chaining
    */
    CsvReader<T> setConverter(String propertyName, PropertyEditor converter);

    /**
    * Sets a field to be ignored for purposes of mapping. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvIgnore)} is used. By default none of the fields are ignored
    * unless, custom instructions are used. In this case, all fields are ignored by default.
    * @param propertyName property which must be ignored for mapping
    * @return convenience for chaining
    */
    CsvReader<T> ignoreProperty(String propertyName);

    /**
    * Maps a column in the CSV to a specific property. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvCell#indexColumn()} is used. By default there is NO mapping
    * when custom instructions are used, so you should roll your own.
    * @param propertyName property to which the index-based mapping must be applied
    * @return convenience for chaining
    */
    CsvReader<T> mapColumnIndexToProperty(int columnIndex, String propertyName);

    /**
    * Maps a column name (which is found in the header) to a specific property. Note that to use this, headers
    * must be enabled. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvCell#name()}
    * is used. By default there is NO mapping when custom instructions are used, so you should roll your own.
    * Also, don't forget to {@link #setMapper(Class)} to
    * {@link nl.tweeenveertig.csveed.bean.ColumnNameMapper} for this to work.
    * @param propertyName property to which the name-based mapping must be applied
    * @return convenience for chaining
    */
    CsvReader<T> mapColumnNameToProperty(String columnName, String propertyName);

}
