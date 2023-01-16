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

public enum EncounteredSymbol {
    SPACE_SYMBOL, SEPARATOR_SYMBOL, QUOTE_SYMBOL(true), ESCAPE_SYMBOL(true), EOL_SYMBOL, EOL_SYMBOL_TRASH(false, true),
    OTHER_SYMBOL, BOM_SYMBOL(false, true), END_OF_FILE_SYMBOL, COMMENT_SYMBOL;

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
