/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.row;

import org.csveed.token.EncounteredSymbol;
import org.csveed.token.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RowInstructionsImpl.
 */
public class RowInstructionsImpl implements RowInstructions {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RowInstructionsImpl.class);

    /** The symbol mapping. */
    private SymbolMapping symbolMapping = new SymbolMapping();

    /** The use header. */
    private boolean useHeader = true;

    /** The skip empty lines. */
    private boolean skipEmptyLines = true;

    /** The settings logged. */
    private boolean settingsLogged;

    /** The quote fields. */
    private boolean quoteFields = true;

    /**
     * Log settings.
     */
    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / skip empty lines? {}", isSkipEmptyLines() ? "yes" : "no");
        LOG.info("- CSV config / has header line? {}", isUseHeader() ? "yes" : "no");
        settingsLogged = true;
    }

    /**
     * Gets the symbol mapping.
     *
     * @return the symbol mapping
     */
    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    /**
     * Checks if is skip empty lines.
     *
     * @return true, if is skip empty lines
     */
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
    public char[] getEndOfLine() {
        return this.symbolMapping.getMappedCharacters(EncounteredSymbol.EOL_SYMBOL);
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
