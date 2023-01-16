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

import java.util.List;

public class RowError extends AbstractCsvError {

    private RowReport report;

    private int lineNumber;

    public RowError(String message, RowReport report, int lineNumber) {
        super(message);
        this.report = report;
        this.lineNumber = lineNumber;
    }

    public RowReport getReport() {
        return report;
    }

    @Override
    public List<String> getPrintableLines() {
        List<String> lines = getMessageAsList();
        List<String> lineReport = report.getPrintableLines();
        lines.add(lineNumber + ": " + lineReport.get(0));
        lines.add(lineNumber + ": " + lineReport.get(1));
        return lines;
    }

    @Override
    public List<RowPart> getRowParts() {
        return report.tokenize();
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

}
