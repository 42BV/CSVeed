package org.csveed.token;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class SymbolMappingTest {

    @Test
    public void addMappingMustEmptyDeleteMapping() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, '\'');
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('\'', ParseState.START_OF_LINE));
        assertNotSame(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.START_OF_LINE));
    }

    @Test
    public void endOfLineWindows() {
        SymbolMapping mapping = new SymbolMapping();
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find(0x0d, ParseState.OUTSIDE_AFTER_FIELD));
        assertEquals(EncounteredSymbol.EOL_SYMBOL_TRASH, mapping.find(0x0a, ParseState.START_OF_LINE));
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find(0x0d, ParseState.START_OF_LINE));
        assertEquals(EncounteredSymbol.EOL_SYMBOL_TRASH, mapping.find(0x0a, ParseState.START_OF_LINE));
    }

    @Test
    public void similarEscapeAndQuote() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '"');
        mapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        assertEquals(EncounteredSymbol.ESCAPE_SYMBOL, mapping.find('"', ParseState.INSIDE_QUOTED_FIELD));
        assertEquals(EncounteredSymbol.QUOTE_SYMBOL, mapping.find('"', ParseState.OUTSIDE_BEFORE_FIELD));
    }

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

    @Test
    public void eolCarriageReturn() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\r', ParseState.ESCAPING));
    }

    @Test
    public void eolLineFeed() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\n', ParseState.INSIDE_FIELD));
    }

    @Test
    public void cannotFind() {
        SymbolMapping mapping = new SymbolMapping();
        assertEquals(EncounteredSymbol.OTHER_SYMBOL, mapping.find('A', ParseState.FINISHED));
    }

}
