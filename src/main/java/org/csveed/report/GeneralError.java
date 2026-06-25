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
