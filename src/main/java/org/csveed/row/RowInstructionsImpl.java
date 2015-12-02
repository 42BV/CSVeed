package org.csveed.row;

import org.csveed.token.EncounteredSymbol;
import org.csveed.token.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowInstructionsImpl implements RowInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(RowInstructionsImpl.class);

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private boolean skipEmptyLines = true;

    private boolean settingsLogged = false;

    private boolean quoteFields = true;

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

    @Override
    public boolean isUseHeader() {
        return useHeader;
    }

    @Override
    public RowInstructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    @Override
    public RowInstructions setStartRow(int startRow) {
        this.symbolMapping.setStartLine(startRow);
        return this;
    }

    @Override
    public char getEscape() {
        return this.symbolMapping.getFirstMappedCharacter(EncounteredSymbol.ESCAPE_SYMBOL);
    }

    @Override
    public RowInstructions setEscape(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, symbol);
        return this;
    }

    @Override
    public char getQuote() {
        return symbolMapping.getFirstMappedCharacter(EncounteredSymbol.QUOTE_SYMBOL);
    }

    @Override
    public RowInstructions setQuotingEnabled(boolean quoteFields) {
        this.quoteFields = quoteFields;
        return this;
    }

    @Override
    public boolean getQuotingEnabled() {
        return quoteFields;
    }

    @Override
    public RowInstructions setQuote(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, symbol);
        return this;
    }

    @Override
    public char getSeparator() {
        return symbolMapping.getFirstMappedCharacter(EncounteredSymbol.SEPARATOR_SYMBOL);
    }

    @Override
    public RowInstructions setSeparator(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, symbol);
        return this;
    }

    @Override
    public RowInstructions setComment(char symbol) {
        this.symbolMapping.addMapping(EncounteredSymbol.COMMENT_SYMBOL, symbol);
        return this;
    }

    @Override
    public char getEndOfLine() {
        return this.symbolMapping.getFirstMappedCharacter(EncounteredSymbol.EOL_SYMBOL);
    }

    @Override
    public RowInstructions setEndOfLine(char[] symbols) {
        this.symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, symbols);
        return this;
    }

    @Override
    public RowInstructions skipEmptyLines(boolean skip) {
        this.skipEmptyLines = skip;
        return this;
    }

    @Override
    public RowInstructions skipCommentLines(boolean skip) {
        this.symbolMapping.setSkipCommentLines(skip);
        return this;
    }

}
