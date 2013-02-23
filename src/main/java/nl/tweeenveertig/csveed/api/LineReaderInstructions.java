package nl.tweeenveertig.csveed.api;

public interface LineReaderInstructions {

    public LineReaderInstructions setUseHeader(boolean useHeader);

    public LineReaderInstructions setStartRow(int startRow);

    public LineReaderInstructions setEscape(char symbol);

    public LineReaderInstructions setQuote(char symbol);

    public LineReaderInstructions setSeparator(char symbol);

    public LineReaderInstructions setEndOfLine(char[] symbols);

}
