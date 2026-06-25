/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
