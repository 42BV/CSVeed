package nl.tweeenveertig.csveed.csv.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SymbolMappingTest {

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
    public void eol() {
        SymbolMapping mapping = new SymbolMapping();
        mapping.addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\r', ParseState.ESCAPING));
        assertEquals(EncounteredSymbol.EOL_SYMBOL, mapping.find('\n', ParseState.INSIDE_FIELD));
    }

    @Test
    public void cannotFind() {
        SymbolMapping mapping = new SymbolMapping();
        assertEquals(EncounteredSymbol.OTHER_SYMBOL, mapping.find('A', ParseState.FINISHED));
    }

}
