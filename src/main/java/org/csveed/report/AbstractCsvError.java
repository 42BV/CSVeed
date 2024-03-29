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
