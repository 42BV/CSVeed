/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.report;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class AbstractCsvError.
 */
public abstract class AbstractCsvError implements CsvError {

    /** The message. */
    private String message;

    /**
     * Instantiates a new abstract csv error.
     *
     * @param message
     *            the message
     */
    protected AbstractCsvError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the message as list.
     *
     * @return the message as list
     */
    protected List<String> getMessageAsList() {
        List<String> lines = new ArrayList<>();
        lines.add(getMessage());
        return lines;
    }

}
