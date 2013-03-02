package nl.tweeenveertig.csveed.row;

/**
* These instructions are used to power the {@link RowReader}. Note that the instructions are also used
* internally if annotations are used.
* @author Robert Bor
*/
public interface LineReaderInstructions {

    /**
    * Makes sure that the first readable line is interpreted as the header line. That line will not be read
    * as content. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#useHeader()}
    * is used. The default value for this setting is true.
    * @param useHeader true if the header is interpreted and used
    * @return convenience for chaining
    */
    public LineReaderInstructions setUseHeader(boolean useHeader);

    /**
    * Sets the start row of the CSV file. If {@link #setUseHeader(boolean)} == true, this will be the header
    * row and the next ones are all content rows. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#startRow()} is used. The default value for this
    * setting is 0.
    * @param startRow the first row to start reading, including the header row
    * @return convenience for chaining
    */
    public LineReaderInstructions setStartRow(int startRow);

    /**
    * Sets the character that will be interpreted as an escape symbol while within a quoted field. This
    * method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#escape()} is used. The
    * default value for this setting is a double quote (") symbol.
    * @param symbol the symbol to use for escaping characters within a quoted field
    * @return convenience for chaining
    */
    public LineReaderInstructions setEscape(char symbol);

    /**
    * Sets the character that will be interpreted as a quote symbol, signifying either the start or the
    * end of a quoted field. This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#quote()}
    * is used. The default value for this setting is a double quote (") symbol.
    * @param symbol the symbol to use for indicating start/end of a quoted field
    * @return convenience for chaining
    */
    public LineReaderInstructions setQuote(char symbol);

    /**
    * Sets the character that will be interpreted as a separator between cells. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#separator()} is used. The default value for this
    * setting is a semi-colon (;).
    * @param symbol the symbol to use as a separator between cells
    * @return convenience for chaining
    */
    public LineReaderInstructions setSeparator(char symbol);

    /**
    * Sets the character that will be interpreted as a comment field on the first position of a row.
    * This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#comment()} is used.
    * The default value for this setting is a hashtag (#).
    * @param symbol the symbol to use as the 0-position comment marker
    * @return convenience for chaining
    */
    public LineReaderInstructions setComment(char symbol);

    /**
    * Sets the characters (plural) that will be interpreted as end-of-line markers (unless within a quoted
    * field). This method is called whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#endOfLine()}
    * is used. The default values for this setting are \r and \n
    * @param symbols the symbol to interpret as end-of-line markers (unless within a quoted field)
    * @return convenience for chaining
    */
    public LineReaderInstructions setEndOfLine(char[] symbols);

    /**
    * Determines whether empty lines must be skipped or treated as single-column rows. This method is called
    * whenever {@link nl.tweeenveertig.csveed.annotations.CsvFile#skipEmptyLines()} is used. The default
    * value for this setting is to skip the empty lines.
    * @param skip true to skip empty lines, false to treat as single-column rows
    * @return convenience for chaining
    */
    LineReaderInstructions skipEmptyLines(boolean skip);

    /**
    * Determines whether comment lines must be skipped. This method is called whenever
    * {@link nl.tweeenveertig.csveed.annotations.CsvFile#skipCommentLines()}  is used. The default
    * value for this setting is to skip comment lines. This method exists to guarantee that lines are
    * not accidentally treated as comment lines.
    * @param skip true to skip comment lines, identified as starting with a comment marker
    * @return convenience for chaining
    */
    LineReaderInstructions skipCommentLines(boolean skip);

}
