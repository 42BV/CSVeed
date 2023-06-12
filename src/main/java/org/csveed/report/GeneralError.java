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
 * The Class GeneralError.
 */
public class GeneralError extends AbstractCsvError {

    /**
     * Instantiates a new general error.
     *
     * @param message
     *            the message
     */
    public GeneralError(String message) {
        super(message);
    }

    @Override
    public List<String> getPrintableLines() {
        return getMessageAsList();
    }

    @Override
    public List<RowPart> getRowParts() {
        return new ArrayList<>();
    }

    @Override
    public int getLineNumber() {
        return -1;
    }
}
