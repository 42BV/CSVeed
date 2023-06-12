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

import static org.csveed.token.EncounteredSymbol.END_OF_FILE_SYMBOL;
import static org.csveed.token.EncounteredSymbol.EOL_SYMBOL;
import static org.csveed.token.EncounteredSymbol.EOL_SYMBOL_TRASH;
import static org.csveed.token.EncounteredSymbol.ESCAPE_SYMBOL;
import static org.csveed.token.EncounteredSymbol.OTHER_SYMBOL;
import static org.csveed.token.EncounteredSymbol.QUOTE_SYMBOL;

import java.util.Map;
import java.util.TreeMap;

import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SymbolMapping {

    private static final Logger LOG = LoggerFactory.getLogger(SymbolMapping.class);

    private Map<EncounteredSymbol, char[]> symbolToChars = new TreeMap<>();
    private Map<Character, EncounteredSymbol> charToSymbol = new TreeMap<>();

    private Character escapeCharacter;

    private Character quoteCharacter;

    private boolean settingsLogged;

    private int startLine = 1;

    private boolean skipCommentLines = true;

    /**
     * The accepted end of line.
     * <p>
     * When multiple EOL characters have been given, only the first one encountered will be accepted.
     */
    private char acceptedEndOfLine;

    public SymbolMapping() {
        initDefaultMapping();
    }

    public void initDefaultMapping() {
        addMapping(EncounteredSymbol.ESCAPE_SYMBOL, '"');
        addMapping(EncounteredSymbol.QUOTE_SYMBOL, '"');
        addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, ';');
        addMapping(EncounteredSymbol.EOL_SYMBOL, new char[] { '\r', '\n' });
        addMapping(EncounteredSymbol.SPACE_SYMBOL, ' ');
        addMapping(EncounteredSymbol.BOM_SYMBOL, '\uFEFF');
        addMapping(EncounteredSymbol.COMMENT_SYMBOL, '#');
    }

    public char getFirstMappedCharacter(EncounteredSymbol encounteredSymbol) {
        char[] mappedCharacters = getMappedCharacters(encounteredSymbol);
        return mappedCharacters == null ? 0 : mappedCharacters[0];
    }

    public char[] getMappedCharacters(EncounteredSymbol encounteredSymbol) {
        return symbolToChars.get(encounteredSymbol);
    }

    public void addMapping(EncounteredSymbol symbol, Character character) {
        addMapping(symbol, new char[] { character });
        if (symbol.isCheckForSimilarEscapeAndQuote()) {
            storeCharacterForLaterComparison(symbol, character);
        }
    }

    public void addMapping(EncounteredSymbol symbol, char[] characters) {
        while (charToSymbol.values().remove(symbol)) {
            // Looping until all symbols removed
        }
        for (Character character : characters) {
            charToSymbol.put(character, symbol);
        }
        symbolToChars.put(symbol, characters);
    }

    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / skip comment lines? {}", isSkipCommentLines() ? "yes" : "no");
        LOG.info("- CSV config / start line: {}", startLine);
        for (Map.Entry<EncounteredSymbol, char[]> entry : symbolToChars.entrySet()) {
            char[] characters = entry.getValue();
            if (LOG.isInfoEnabled()) {
                LOG.info("- CSV config / Characters for {} {}", entry.getKey(), charactersToString(characters));
            }
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
        switch (character) {
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            default:
                return Character.toString(character);
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
        EncounteredSymbol symbol = charToSymbol.get((char) character);
        if (symbol == null) {
            return OTHER_SYMBOL;
        }
        if (symbol == EOL_SYMBOL) {
            if (acceptedEndOfLine == 0) {
                LOG.info("- Triggering EOL character: {}", character);
                acceptedEndOfLine = (char) character;
            }
            if (acceptedEndOfLine != character) {
                symbol = EOL_SYMBOL_TRASH;
            }
        }
        if (symbol.isCheckForSimilarEscapeAndQuote() && isSameCharactersForEscapeAndQuote()) {
            return parseState.isUpgradeQuoteToEscape() ? ESCAPE_SYMBOL : QUOTE_SYMBOL;
        }
        return symbol;
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
