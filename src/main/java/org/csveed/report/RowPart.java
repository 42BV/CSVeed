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
package org.csveed.report;

/**
 * The Class RowPart.
 */
public class RowPart {

    /** The token. */
    private String token;

    /** The highlight. */
    private boolean highlight;

    /**
     * Instantiates a new row part.
     *
     * @param token
     *            the token
     * @param highlight
     *            the highlight
     */
    public RowPart(String token, boolean highlight) {
        this.token = token;
        this.highlight = highlight;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Checks if is highlight.
     *
     * @return true, if is highlight
     */
    public boolean isHighlight() {
        return highlight;
    }
}
