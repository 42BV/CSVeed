package nl.tweeenveertig.csveed.parser;

public enum EncounteredSymbol {
    SEPARATOR_SYMBOL,
    QUOTE_SYMBOL(true),
    ESCAPE_SYMBOL(true),
    EOL_SYMBOL,
    OTHER_SYMBOL,
    END_OF_FILE_SYMBOL;

    private boolean checkForSimilarEscapeAndQuote = false;

    private EncounteredSymbol() {}

    private EncounteredSymbol(boolean checkForSimilarEscapeAndQuote) {
        this.checkForSimilarEscapeAndQuote = true;
    }

    public boolean isCheckForSimilarEscapeAndQuote() {
        return this.checkForSimilarEscapeAndQuote;
    }
}
