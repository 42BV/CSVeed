package org.csveed.token;

import org.csveed.report.CsvError;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

import static org.csveed.token.EncounteredSymbol.*;

public class SymbolMapping {

    public static final Logger LOG = LoggerFactory.getLogger(SymbolMapping.class);

    private Map<EncounteredSymbol, char[]> symbolToChars = new TreeMap<EncounteredSymbol, char[]>();
    private Map<Character, EncounteredSymbol> charToSymbol = new TreeMap<Character, EncounteredSymbol>();

    private Character escapeCharacter;

    private Character quoteCharacter;

    private boolean settingsLogged = false;

    private int startLine = 1;

    private boolean skipCommentLines = true;

    private char acceptedEndOfLine = 0; // When multiple EOL characters have been given,
                                        // only the first one encountered will be accepted.

    public SymbolMapping() {
        initDefaultMapping();
    }

    public void initDefaultMapping() {
        addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '"');
        addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, ';');
        addMapping(EncounteredSymbol.EOL_SYMBOL, new char[]{ '\r', '\n' } );
        addMapping(EncounteredSymbol.SPACE_SYMBOL, ' ');
        addMapping(EncounteredSymbol.COMMENT_SYMBOL, '#');
    }

    public void addMapping(EncounteredSymbol symbol, Character character) {
        addMapping(symbol, new char[] { character } );
        if (symbol.isCheckForSimilarEscapeAndQuote()) {
            storeCharacterForLaterComparison(symbol, character);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void addMapping(EncounteredSymbol symbol, char[] characters) {
        while (charToSymbol.values().remove(symbol));
        for (Character character : characters) {
            charToSymbol.put(character, symbol);
        }
        symbolToChars.put(symbol, characters);
    }

    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / skip comment lines? "+(isSkipCommentLines()?"yes":"no"));
        LOG.info("- CSV config / start line: "+startLine);
        for (EncounteredSymbol symbol : symbolToChars.keySet()) {
            char[] characters = symbolToChars.get(symbol);
            LOG.info("- CSV config / Characters for "+symbol+" "+charactersToString(characters));
        }
        settingsLogged = true;
    }

    private String charactersToString(char[] characters) {
        StringBuilder returnString = new StringBuilder();
        for (char currentChar : characters) {
            returnString.append(charToPrintable(currentChar));
            returnString.append(" ");
        }
        return returnString.toString();
    }

    private String charToPrintable(char character) {
        switch(character) {
            case '\t' : return "\\t";
            case '\n' : return "\\n";
            case '\r' : return "\\r";
            default: return Character.toString(character);
        }
    }

    private void storeCharacterForLaterComparison(EncounteredSymbol symbol, Character character) {
        if (symbol == ESCAPE_SYMBOL) {
            escapeCharacter = character;
        } else if (symbol == QUOTE_SYMBOL) {
            quoteCharacter = character;
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
        if (symbol == EOL_SYMBOL) {
            if (acceptedEndOfLine == 0) {
                acceptedEndOfLine = (char)character;
            }
            if (acceptedEndOfLine != character) {
                symbol = EOL_SYMBOL_TRASH;
            }
        }
        if (symbol.isCheckForSimilarEscapeAndQuote() && isSameCharactersForEscapeAndQuote()) {
            return parseState.isUpgradeQuoteToEscape() ? ESCAPE_SYMBOL : QUOTE_SYMBOL;
        } else {
            return symbol;
        }
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        if (startLine == 0) {
            throw new CsvException(new GeneralError("Row cannot be set at 0. Rows are 1-based"));
        }
        this.startLine = startLine;
    }

    public boolean isSkipCommentLines() {
        return skipCommentLines;
    }

    public void setSkipCommentLines(boolean skipCommentLines) {
        this.skipCommentLines = skipCommentLines;
    }
}
