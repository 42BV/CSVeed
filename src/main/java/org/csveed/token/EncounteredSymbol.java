package org.csveed.token;

public enum EncounteredSymbol {
    SPACE_SYMBOL,
    SEPARATOR_SYMBOL,
    QUOTE_SYMBOL(true),
    ESCAPE_SYMBOL(true),
    EOL_SYMBOL,
    EOL_SYMBOL_TRASH(false, true),
    OTHER_SYMBOL,
    END_OF_FILE_SYMBOL,
    COMMENT_SYMBOL;

    private boolean checkForSimilarEscapeAndQuote = false;

    private boolean trash = false;

    private EncounteredSymbol() {
        this(false, false);
    }

    private EncounteredSymbol(boolean checkForSimilarEscapeAndQuote) {
        this(checkForSimilarEscapeAndQuote, false);
    }

    private EncounteredSymbol(boolean checkForSimilarEscapeAndQuote, boolean trash) {
        this.checkForSimilarEscapeAndQuote = checkForSimilarEscapeAndQuote;
        this.trash = trash;
    }

    public boolean isCheckForSimilarEscapeAndQuote() {
        return this.checkForSimilarEscapeAndQuote;
    }

    public boolean isTrash() {
        return this.trash;
    }

}
