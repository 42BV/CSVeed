/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class CsvException.
 */
public class CsvException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(CsvException.class);

    /** The error. */
    private CsvError error;

    /**
     * Instantiates a new csv exception.
     *
     * @param error
     *            the error
     */
    public CsvException(CsvError error) {
        this.error = error;
        for (String line : error.getPrintableLines()) {
            LOG.error("{}", line);
        }
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public CsvError getError() {
        return this.error;
    }

    @Override
    public String getMessage() {
        return this.getError().getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder errorMessage = new StringBuilder();
        boolean first = true;
        for (String line : getError().getPrintableLines()) {
            if (!first) {
                errorMessage.append(System.lineSeparator());
            }
            errorMessage.append(line);
            first = false;
        }
        return errorMessage.toString();
    }

}
