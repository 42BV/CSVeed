package org.csveed.api;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.csveed.bean.AbstractMapper;
import org.csveed.bean.conversion.Converter;

/**
* <p>
*     The CsvClient is responsible for reading/writing CSV rows and converting those into beans/rows. There are a couple
*     of ways to initialize a {@link org.csveed.api.CsvClientImpl}:
* </p>
*
* <ul>
*     <li>Just use a Reader/Writer. In this case you can only use the row functionality of CSVeed,
*         not the bean functionality</li>
*     <li>Pass a Reader or Writer and point it to class. The {@link org.csveed.annotations} in the class are read,
*         as is the order
*         of the properties within the class.</li>
*     <li>Pass a Reader or Writer and roll your own instructions. Pass a {@link org.csveed.bean.BeanInstructions}
*         implementation with your own configuration settings</li>
* </ul>
*
* @param <T> the bean class into which the rows are converted
* @author Robert Bor
*/
public interface CsvClient<T> {

    /**
    * Writes a collection of Beans to the table
    * @param beans beans to write to the table
    */
    void writeBeans(Collection<T> beans);

    /**
    * Writes a single Bean to the table
    * @param bean bean to write to the table
    */
    void writeBean(T bean);

    /**
    * Writes a single row to the table
    * @param row single row
    */
    void writeRow(Row row);

    /**
    * Writes a single row (consisting of String cells) to the table
    * @param row single row
    * @return the written row
    */
    Row writeRow(String[] row);

    /**
    * Writes a collection of rows to the table
    * @param rows collections of rows
    */
    public void writeRows(Collection<Row> rows);

    /**
    * Writes a two-dimensional array of cells (rows with cells) to the table
    * @param rows two-dimensional array of cells
    */
    void writeRows(String[][] rows);

    /**
    * Writes the header to the table
    * @param header the header row
    * @return the converted Header
    */
    Header writeHeader(String[] header);

    /**
    * Writes the header to the table
    * @param header the header row
    */
    void writeHeader(Header header);

    /**
     * Writes a header based on the bean properties to the table
     */
    void writeHeader();

    /**
    * Reads all rows from the file and return these as beans.
    * @return all beans read from the Reader
    */
    List<T> readBeans();

    /**
    * Reads a single row and returns this as a bean. The RowReader will keep track of its state.
    * @return Bean read from the Reader
    */
    T readBean();

    /**
    * Reads all rows from the file and returns them as a List. After this, the RowReader will be finished
    * @return all Rows read from the Reader
    */
    List<Row> readRows();

    /**
    * Reads a single row from the file and returns this. The RowReader will keep track of its state.
    * @return Row read from the Reader
    */
    Row readRow();

    /**
    * Returns the header of the CSV file. Only possibly returns a value when useHeader==true
    * @return header or null if the useHeader==false
    */
    Header readHeader();

    /**
    * Returns the line from which the row was read. Note that a line is seen as a legitimate CSV row, not
    * necessarily a printable line (unless multi-lines are used, these values are the same).
    * @return current line number
    */
    int getCurrentLine();

    /**
    * States whether the Reader is done with the file
    * @return true if file is finished
    */
    boolean isFinished();

    /**
    * Makes sure that the first readable line is interpreted as the header line. That line will not be read
    * as content. This method is called whenever {@link org.csveed.annotations.CsvFile#useHeader()}
    * is used. The default value for this setting is true. This call is a facade for
    * {@link org.csveed.row.RowInstructions#setUseHeader(boolean)}.
    * @param useHeader true if the header is interpreted and used
    * @return convenience for chaining
    */
    CsvClient<T> setUseHeader(boolean useHeader);

    /**
    * Sets the start row of the CSV file. If {@link #setUseHeader(boolean)} == true, this will be the header
    * row and the next ones are all content rows. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#startRow()} is used. The default value for this
    * setting is 1. This call is a facade for {@link org.csveed.row.RowInstructions#setStartRow(int)}.
    * @param startRow the first row to start reading, including the header row
    * @return convenience for chaining
    */
    CsvClient<T> setStartRow(int startRow);

    /**
    * Sets the character that will be interpreted as an escape symbol while within a quoted field. This
    * method is called whenever {@link org.csveed.annotations.CsvFile#escape()} is used. The
    * default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link org.csveed.row.RowInstructions#setEscape(char)}.
    * @param symbol the symbol to use for escaping characters within a quoted field
    * @return convenience for chaining
    */
    CsvClient<T> setEscape(char symbol);

    /**
    * Sets the character that will be interpreted as a quote symbol, signifying either the start or the
    * end of a quoted field. This method is called whenever {@link org.csveed.annotations.CsvFile#quote()}
    * is used. The default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link org.csveed.row.RowInstructions#setQuote(char)}.
    * @param symbol the symbol to use for indicating start/end of a quoted field
    * @return convenience for chaining
    */
    CsvClient<T> setQuote(char symbol);

    /**
    * Sets the character that will be interpreted as a separator between cells. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#separator()} is used. The default value for this
    * setting is a semi-colon (;). This call is a facade for {@link org.csveed.row.RowInstructions#setSeparator(char)}.
    * @param symbol the symbol to use as a separator between cells
    * @return convenience for chaining
    */
    CsvClient<T> setSeparator(char symbol);

    /**
    * Sets the character that will be interpreted as a comment field on the first position of a row.
    * This method is called whenever {@link org.csveed.annotations.CsvFile#comment()} is used.
    * The default value for this setting is a hashtag (#).
    * @param symbol the symbol to use as the 0-position comment marker
    * @return convenience for chaining
    */
    CsvClient<T> setComment(char symbol);

    /**
    * Sets the characters (plural) that will be interpreted as end-of-line markers (unless within a quoted
    * field). This method is called whenever {@link org.csveed.annotations.CsvFile#endOfLine()}
    * is used. The default values for this setting are \r and \n.  This call is a facade for
    * {@link org.csveed.row.RowInstructions#setEndOfLine(char[])}.
    * @param symbols the symbol to interpret as end-of-line markers (unless within a quoted field)
    * @return convenience for chaining
    */
    CsvClient<T> setEndOfLine(char[] symbols);

    /**
    * Determines whether empty lines must be skipped or treated as single-column rows. This method is called
    * whenever {@link org.csveed.annotations.CsvFile#skipEmptyLines()} is used. The default
    * value for this setting is to skip the empty lines.
    * @param skip true to skip empty lines, false to treat as single-column rows
    * @return convenience for chaining
    */
    CsvClient<T> skipEmptyLines(boolean skip);

    /**
    * Determines whether comment lines must be skipped. This method is called whenever
    * {@link org.csveed.annotations.CsvFile#skipCommentLines()}  is used. The default
    * value for this setting is to skip comment lines. This method exists to guarantee that lines are
    * not accidentally treated as comment lines.
    * @param skip true to skip comment lines, identified as starting with a comment marker
    * @return convenience for chaining
    */
    CsvClient<T> skipCommentLines(boolean skip);

    /**
    * A file can have a special layout with a dynamic number of columns. If the intention is to duplicate rows
    * for every separate column, this is the method you require. It will remember the start position of the
    * dynamic columns and treat every column after that as dynamic. For every dynamic column a row will be
    * created. If a bean has fields annotated with @CsvHeaderName or @CsvHeaderValue, it will store the
    * values of the header or the cell for that index column in the fields.
    * @param startIndex the index where the dynamic columns start
    * @return convenience for chaining
    */
    CsvClient<T> setStartIndexDynamicColumns(int startIndex);

    /**
    * Determines which mapping strategy is to be employed for mapping cells to bean properties. This
    * method is called whenever {@link org.csveed.annotations.CsvFile#mappingStrategy()} is
    * used. The default mapping strategy is {@link org.csveed.bean.ColumnIndexMapper}, which
    * looks at either the position of a property within the class or the custom index if
    * {@link org.csveed.annotations.CsvCell#columnIndex()} or {@link #mapColumnIndexToProperty(int, String)}
    * has been set.
    * @param mapper the mapping strategy to employ for mapping cells to bean properties
    * @return convenience for chaining
    */
    CsvClient<T> setMapper(Class<? extends AbstractMapper> mapper);

    /**
    * Determines what dateformat to apply to the cell value before storing it as a date. This method is called
    * whenever {@link org.csveed.annotations.CsvDate} is used. The default for date format is
    * dd-MM-yyyy.
    * @param propertyName the name of the property to write the date to
    * @param dateFormat the date format to apply for parsing the date value
    * @return convenience for chaining
    */
    CsvClient<T> setDate(String propertyName, String dateFormat);

    /**
    * Determines what Locale to apply to the cell value before converting it to a number. This method is called
    * whenever {@link org.csveed.annotations.CsvLocalizedNumber} is used. The default for Locale is the Locale
    * of the server.
    * @param propertyName the name of the property to write the data to
    * @param locale the Locale to apply for converting the number
    * @return convenience for chaining
    */
    CsvClient<T> setLocalizedNumber(String propertyName, Locale locale);

    /**
    * Determines if the field is required. If so, the cell may not be empty and a
    * {@link org.csveed.report.CsvException} will be thrown if this occurs. This method is called
    * whenever {@link org.csveed.annotations.CsvCell#required()} is used. The default for a property
    * is false.
    * @param propertyName property for which the requirement applies
    * @param required whether the cell must be not-null
    * @return convenience for chaining
    */
    CsvClient<T> setRequired(String propertyName, boolean required);

    /**
    * Sets a custom {@link java.beans.PropertyEditor} for the property. This PropertyEditor is called to convert the
    * text to the type of the property and set it on the bean. This method is called whenever
    * {@link org.csveed.annotations.CsvConverter#converter()} is used. The default for a property
    * is based on the wonderful set of PropertyEditors that Spring offers, which is all basics and some extras
    * as well.
    * @param propertyName property to which the converter must be applied
    * @param converter PropertyEditor to apply to the property
    * @return convenience for chaining
    */
    CsvClient<T> setConverter(String propertyName, Converter converter);

    /**
    * Sets a field to be ignored for purposes of mapping. This method is called whenever
    * {@link org.csveed.annotations.CsvIgnore} is used. By default none of the fields are ignored
    * unless, custom instructions are used. In this case, all fields are ignored by default.
    * @param propertyName property which must be ignored for mapping
    * @return convenience for chaining
    */
    CsvClient<T> ignoreProperty(String propertyName);

    /**
    * Maps a column in the CSV to a specific property. This method is called whenever
    * {@link org.csveed.annotations.CsvCell#columnIndex()} is used. By default there is NO mapping
    * when custom instructions are used, so you should roll your own. Note that column indexes are
    * 1-based, not 0-based.
    * @param columnIndex index of the column for which the property mapping must be applied
    * @param propertyName property to which the index-based mapping must be applied
    * @return convenience for chaining
    */
    CsvClient<T> mapColumnIndexToProperty(int columnIndex, String propertyName);

    /**
    * Maps a column name (which is found in the header) to a specific property. Note that to use this, headers
    * must be enabled. This method is called whenever {@link org.csveed.annotations.CsvCell#columnName()}
    * is used. By default there is NO mapping when custom instructions are used, so you should roll your own.
    * Also, don't forget to {@link #setMapper(Class)} to
    * {@link org.csveed.bean.ColumnNameMapper} for this to work.
    * @param columnName name of the column for which the property mapping must be applied
    * @param propertyName property to which the name-based mapping must be applied
    * @return convenience for chaining
    */
    CsvClient<T> mapColumnNameToProperty(String columnName, String propertyName);

    /**
    * Determines what property will receive the header name in the currently active dynamic column
    * @param propertyName property in which the active dynamic header name must be stored
    * @return convenience for chaining
    */
    CsvClient<T> setHeaderNameToProperty(String propertyName);

    /**
    * Determines what property will receive the cell value in the currently active dynamic column
    * @param propertyName property in which the active dynamic column value must be stored
    * @return convenience for chaining
    */
    CsvClient<T> setHeaderValueToProperty(String propertyName);

}
