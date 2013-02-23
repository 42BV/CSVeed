package nl.tweeenveertig.csveed.line;

import nl.tweeenveertig.csveed.token.EncounteredSymbol;
import nl.tweeenveertig.csveed.token.SymbolMapping;

public class CsvInstructions {

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private int startRow = 0;

    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public CsvInstructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    public CsvInstructions setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public CsvInstructions setEscape(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, symbol);
        return this;
    }

    public CsvInstructions setQuote(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, symbol);
        return this;
    }

    public CsvInstructions setSeparator(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, symbol);
        return this;
    }

    public CsvInstructions setEndOfLine(char[] symbols) {
        this.symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, symbols);
        return this;
    }

}
