package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.token.SymbolMapping;

public interface LineReaderInstructions {

    public SymbolMapping getSymbolMapping();

    public boolean isUseHeader();

    public int getStartRow();

    public LineReaderInstructions setUseHeader(boolean useHeader);

    public LineReaderInstructions setStartRow(int startRow);

    public LineReaderInstructions setEscape(char symbol);

    public LineReaderInstructions setQuote(char symbol);

    public LineReaderInstructions setSeparator(char symbol);

    public LineReaderInstructions setEndOfLine(char[] symbols);

}
