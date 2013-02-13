package nl.tweeenveertig.csveed.parser;

import java.util.*;

import static nl.tweeenveertig.csveed.parser.EncounteredSymbol.*;

public class SymbolMapping {

    private Map<Character, EncounteredSymbol> charToSymbol = new TreeMap<Character, EncounteredSymbol>();

    private Character escapeCharacter;

    private Character quoteCharacter;

    public SymbolMapping() {
        initDefaultMapping();
    }

    public void initDefaultMapping() {
        addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '"');
        addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, ';');
        addMapping(EncounteredSymbol.EOL_SYMBOL, new Character[]{ '\r', '\n' } );
    }

    public void addMapping(EncounteredSymbol symbol, Character character) {
        addMapping(symbol, new Character[] { character } );
        if (symbol.isCheckForSimilarEscapeAndQuote()) {
            storeCharacterForLaterComparison(symbol, character);
        }
    }

    private void storeCharacterForLaterComparison(EncounteredSymbol symbol, Character character) {
        if (symbol == ESCAPE_SYMBOL) {
            escapeCharacter = character;
        } else if (symbol == QUOTE_SYMBOL) {
            quoteCharacter = character;
        }
    }

    public void addMapping(EncounteredSymbol symbol, Character[] characters) {
        for (Character character : characters) {
            charToSymbol.put(character, symbol);
        }
    }

    public boolean isSameCharactersForEscapeAndQuote() {
        return escapeCharacter != null && quoteCharacter != null && escapeCharacter.equals(quoteCharacter);
    }

    public EncounteredSymbol find(int character, ParseState parseState) {
        if (character == -1) {
            return END_OF_FILE_SYMBOL;
        }
        EncounteredSymbol symbol = charToSymbol.get((char)character);
        if (symbol == null) {
            return OTHER_SYMBOL;
        }
        if (symbol.isCheckForSimilarEscapeAndQuote() && isSameCharactersForEscapeAndQuote()) {
            return parseState.isUpgradeQuoteToEscape() ? ESCAPE_SYMBOL : QUOTE_SYMBOL;
        } else {
            return symbol;
        }
    }

}
