package nl.tweeenveertig.csveed.line;

import nl.tweeenveertig.csveed.token.EncounteredSymbol;
import nl.tweeenveertig.csveed.token.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineReaderInstructionsImpl implements LineReaderInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(LineReaderInstructionsImpl.class);

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
    public LineReaderInstructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    @Override
    public LineReaderInstructions setStartRow(int startRow) {
        this.symbolMapping.setStartLine(startRow);
        return this;
    }

    @Override
    public LineReaderInstructions setEscape(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, symbol);
        return this;
    }

    @Override
    public LineReaderInstructions setQuote(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, symbol);
        return this;
    }

    @Override
    public LineReaderInstructions setSeparator(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, symbol);
        return this;
    }

    @Override
    public LineReaderInstructions setComment(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.COMMENT_SYMBOL, symbol);
        return this;
    }

    @Override
    public LineReaderInstructions setEndOfLine(char[] symbols) {
        this.symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, symbols);
        return this;
    }

    @Override
    public LineReaderInstructions skipEmptyLines(boolean skip) {
        this.skipEmptyLines = skip;
        return this;
    }

    @Override
    public LineReaderInstructions skipCommentLines(boolean skip) {
        this.symbolMapping.setSkipCommentLines(skip);
        return this;
    }

}
