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

/**
 * The Enum EncounteredSymbol.
 */
public enum EncounteredSymbol {

    /** The space symbol. */
    SPACE_SYMBOL,
    /** The separator symbol. */
    SEPARATOR_SYMBOL,
    /** The quote symbol. */
    QUOTE_SYMBOL(true),
    /** The escape symbol. */
    ESCAPE_SYMBOL(true),
    /** The eol symbol. */
    EOL_SYMBOL,
    /** The eol symbol trash. */
    EOL_SYMBOL_TRASH(false, true),

    /** The other symbol. */
    OTHER_SYMBOL,
    /** The bom symbol. */
    BOM_SYMBOL(false, true),
    /** The end of file symbol. */
    END_OF_FILE_SYMBOL,
    /** The comment symbol. */
    COMMENT_SYMBOL;

    /** The check for similar escape and quote. */
    private final boolean checkForSimilarEscapeAndQuote;

    /** The trash. */
    private final boolean trash;

    /**
     * Instantiates a new encountered symbol.
     */
    EncounteredSymbol() {
        this(false, false);
    }

    /**
     * Instantiates a new encountered symbol.
     *
     * @param checkForSimilarEscapeAndQuote
     *            the check for similar escape and quote
     */
    EncounteredSymbol(boolean checkForSimilarEscapeAndQuote) {
        this(checkForSimilarEscapeAndQuote, false);
    }

    /**
     * Instantiates a new encountered symbol.
     *
     * @param checkForSimilarEscapeAndQuote
     *            the check for similar escape and quote
     * @param trash
     *            the trash
     */
    EncounteredSymbol(boolean checkForSimilarEscapeAndQuote, boolean trash) {
        this.checkForSimilarEscapeAndQuote = checkForSimilarEscapeAndQuote;
        this.trash = trash;
    }

    /**
     * Checks if is check for similar escape and quote.
     *
     * @return true, if is check for similar escape and quote
     */
    public boolean isCheckForSimilarEscapeAndQuote() {
        return this.checkForSimilarEscapeAndQuote;
    }

    /**
     * Checks if is trash.
     *
     * @return true, if is trash
     */
    public boolean isTrash() {
        return this.trash;
    }

}
