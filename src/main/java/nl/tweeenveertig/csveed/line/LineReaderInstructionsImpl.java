package nl.tweeenveertig.csveed.line;

import nl.tweeenveertig.csveed.token.EncounteredSymbol;
import nl.tweeenveertig.csveed.token.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineReaderInstructionsImpl implements LineReaderInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(LineReaderInstructionsImpl.class);

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private int startRow = 0;

    private boolean settingsLogged = false;

    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / start line: "+getStartRow());
        LOG.info("- CSV config / has header line? "+(isUseHeader()?"yes":"no"));
        settingsLogged = true;
    }

    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public LineReaderInstructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    public LineReaderInstructions setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public LineReaderInstructions setEscape(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, symbol);
        return this;
    }

    public LineReaderInstructions setQuote(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, symbol);
        return this;
    }

    public LineReaderInstructions setSeparator(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, symbol);
        return this;
    }

    public LineReaderInstructions setEndOfLine(char[] symbols) {
        this.symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, symbols);
        return this;
    }

}
