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
