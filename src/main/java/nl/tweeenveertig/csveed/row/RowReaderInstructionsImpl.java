package nl.tweeenveertig.csveed.row;

import nl.tweeenveertig.csveed.token.EncounteredSymbol;
import nl.tweeenveertig.csveed.token.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowReaderInstructionsImpl implements RowReaderInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(RowReaderInstructionsImpl.class);

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private boolean skipEmptyLines = true;

    private boolean settingsLogged = false;

    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / skip empty lines? "+(isSkipEmptyLines()?"yes":"no"));
        LOG.info("- CSV config / has header line? "+(isUseHeader()?"yes":"no"));
        settingsLogged = true;
    }

    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    public boolean isSkipEmptyLines() {
        return skipEmptyLines;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    @Override
    public RowReaderInstructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    @Override
    public RowReaderInstructions setStartRow(int startRow) {
        this.symbolMapping.setStartLine(startRow);
        return this;
    }

    @Override
    public RowReaderInstructions setEscape(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, symbol);
        return this;
    }

    @Override
    public RowReaderInstructions setQuote(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, symbol);
        return this;
    }

    @Override
    public RowReaderInstructions setSeparator(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, symbol);
        return this;
    }

    @Override
    public RowReaderInstructions setComment(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.COMMENT_SYMBOL, symbol);
        return this;
    }

    @Override
    public RowReaderInstructions setEndOfLine(char[] symbols) {
        this.symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, symbols);
        return this;
    }

    @Override
    public RowReaderInstructions skipEmptyLines(boolean skip) {
        this.skipEmptyLines = skip;
        return this;
    }

    @Override
    public RowReaderInstructions skipCommentLines(boolean skip) {
        this.symbolMapping.setSkipCommentLines(skip);
        return this;
    }

}
