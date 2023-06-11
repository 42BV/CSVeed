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
package org.csveed.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.report.CsvException;
import org.junit.jupiter.api.Test;

/**
 * The Class SymbolMappingTest.
 */
public class SymbolMappingTest {

    /**
     * Sets the row at zero.
     */
    @Test
    public void setRowAtZero() {
        SymbolMapping mapping = new SymbolMapping();
        assertThrows(CsvException.class, () -> {
            mapping.setStartLine(0);
        });
    }

    /**
     * Adds the mapping must empty delete mapping.
     */
    @Test
    public void addMappingMustEmptyDeleteMapping() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, '\'');
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('\'', ParseState.START_OF_LINE));
        assertNotSame(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.START_OF_LINE));
    }

    /**
     * End of line windows.
     */
    @Test
    public void endOfLineWindows() {
        SymbolMapping mapping = new SymbolMapping();
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find(0x0d, ParseState.OUTSIDE_AFTER_FIELD));
        assertEquals(EncounteredSymbol.EOL_SYMBOL_TRASH, mapping.find(0x0a, ParseState.START_OF_LINE));
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find(0x0d, ParseState.START_OF_LINE));
        assertEquals(EncounteredSymbol.EOL_SYMBOL_TRASH, mapping.find(0x0a, ParseState.START_OF_LINE));
    }

    /**
     * Similar escape and quote.
     */
    @Test
    public void similarEscapeAndQuote() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '"');
        mapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        assertEquals(EncounteredSymbol.ESCAPE_SYMBOL, mapping.find('"', ParseState.INSIDE_QUOTED_FIELD));
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.OUTSIDE_BEFORE_FIELD));
    }

    /**
     * Dissimilar escape and quote.
     */
    @Test
    public void dissimilarEscapeAndQuote() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '\\');
        mapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        assertEquals(EncounteredSymbol.ESCAPE_SYMBOL, mapping.find('\\', ParseState.OUTSIDE_BEFORE_FIELD));
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.OUTSIDE_BEFORE_FIELD));
        assertEquals(EncounteredSymbol.ESCAPE_SYMBOL, mapping.find('\\', ParseState.INSIDE_QUOTED_FIELD));
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.INSIDE_QUOTED_FIELD));
    }

    /**
     * Eol carriage return.
     */
    @Test
    public void eolCarriageReturn() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\r', ParseState.ESCAPING));
    }

    /**
     * Eol line feed.
     */
    @Test
    public void eolLineFeed() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\n', ParseState.INSIDE_FIELD));
    }

    /**
     * Bom symbol.
     */
    @Test
    public void bomSymbol() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.BOM_SYMBOL, '\uFEFF');
        assertEquals(EncounteredSymbol.BOM_SYMBOL, mapping.find('\uFEFF', ParseState.START_OF_LINE));
    }

    /**
     * Cannot find.
     */
    @Test
    public void cannotFind() {
        SymbolMapping mapping = new SymbolMapping();
        assertEquals(EncounteredSymbol.OTHER_SYMBOL, mapping.find('A', ParseState.FINISHED));
    }
}
