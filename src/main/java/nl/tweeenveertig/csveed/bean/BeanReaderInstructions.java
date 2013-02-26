package nl.tweeenveertig.csveed.bean;

import java.beans.PropertyEditor;

/**
* These instructions are used to power the {@link BeanReader}. Note that the instructions are also used
* internally if annotations are used.
* @param <T> bean to use for the conversion process
* @author Robert Bor
*/
public interface BeanReaderInstructions<T> {

    /**
    * Makes sure that the first readable line is interpreted as the header line. That line will not be read
    * as content. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#useHeader()}
    * is used. The default value for this setting is true. This call is a facade for
     * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setUseHeader(boolean)}.
    * @param useHeader true if the header is interpreted and used
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setUseHeader(boolean useHeader);

    /**
    * Sets the start row of the CSV file. If {@link #setUseHeader(boolean)} == true, this will be the header
    * row and the next ones are all content rows. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#startRow()} is used. The default value for this
    * setting is 0. This call is a facade for {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setStartRow(int)}.
    * @param startRow the first row to start reading, including the header row
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setStartRow(int startRow);

    /**
    * Sets the character that will be interpreted as an escape symbol while within a quoted field. This
    * method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#escape()} is used. The
    * default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setEscape(char)}.
    * @param symbol the symbol to use for escaping characters within a quoted field
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setEscape(char symbol);

    /**
    * Sets the character that will be interpreted as a quote symbol, signifying either the start or the
    * end of a quoted field. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#quote()}
    * is used. The default value for this setting is a double quote (") symbol. This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setQuote(char)}.
    * @param symbol the symbol to use for indicating start/end of a quoted field
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setQuote(char symbol);

    /**
    * Sets the character that will be interpreted as a separator between cells. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#separator()} is used. The default value for this
    * setting is a semi-colon (;). This call is a facade for {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setSeparator(char)}.
    * @param symbol the symbol to use as a separator between cells
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setSeparator(char symbol);

    /**
    * Sets the characters (plural) that will be interpreted as end-of-line markers (unless within a quoted
    * field). This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#endOfLine()}
    * is used. The default values for this setting are \r and \n.  This call is a facade for
    * {@link nl.tweeenveertig.csveed.line.LineReaderInstructions#setEndOfLine(char[])}.
    * @param symbols the symbol to interpret as end-of-line markers (unless within a quoted field)
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setEndOfLine(char[] symbols);

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
    BeanReaderInstructions<T> setMapper(Class<? extends AbstractMapper> mapper);

    /**
    * Determines what dateformat to apply to the cell value before storing it as a date. This method is called
    * whenever {@link nl.tweeenveertig.csveed.annotations.CsvDate} is used. The default for date format is
    * dd-MM-yyyy.
    * @param propertyName the name of the property to write the date to
    * @param dateFormat the date format to apply for parsing the date value
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setDate(String propertyName, String dateFormat);

    /**
    * Determines if the field is required. If so, the cell may not be empty and a
    * {@link nl.tweeenveertig.csveed.report.CsvException} will be thrown if this occurs. This method is called
    * whenever {@link nl.tweeenveertig.csveed.annotations.CsvCell#required()} is used. The default for a property
    * is false.
    * @param propertyName property for which the requirement applies
    * @param required whether the cell must be not-null
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setRequired(String propertyName, boolean required);

    /**
    * Sets a custom {@link PropertyEditor} for the property. This PropertyEditor is called to convert the
    * text to the type of the property and set it on the bean. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvConverter#converter()} is used. The default for a property
    * is based on the wonderful set of PropertyEditors that Spring offers, which is all basics and some extras
    * as well.
    * @param propertyName property to which the converter must be applied
    * @param converter PropertyEditor to apply to the property
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> setConverter(String propertyName, PropertyEditor converter);

    /**
    * Sets a field to be ignored for purposes of mapping. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvIgnore)} is used. By default none of the fields are ignored
    * unless, custom instructions are used. In this case, all fields are ignored by default.
    * @param propertyName property which must be ignored for mapping
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> ignoreProperty(String propertyName);

    /**
    * Maps a column in the CSV to a specific property. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvCell#indexColumn()} is used. By default there is NO mapping
    * when custom instructions are used, so you should roll your own.
    * @param propertyName property to which the index-based mapping must be applied
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> mapColumnIndexToProperty(int columnIndex, String propertyName);

    /**
    * Maps a column name (which is found in the header) to a specific property. Note that to use this, headers
    * must be enabled. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvCell#name()}
    * is used. By default there is NO mapping when custom instructions are used, so you should roll your own.
    * Also, don't forget to {@link #setMapper(Class)} to
    * {@link nl.tweeenveertig.csveed.bean.ColumnNameMapper} for this to work.
    * @param propertyName property to which the name-based mapping must be applied
    * @return convenience for chaining
    */
    BeanReaderInstructions<T> mapColumnNameToProperty(String columnName, String propertyName);

}
