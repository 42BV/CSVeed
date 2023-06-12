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
 * The Class RowReport.
 */
public class RowReport {

    /** The row. */
    private String row;

    /** The start. */
    private int start;

    /** The end. */
    private int end;

    /**
     * Instantiates a new row report.
     *
     * @param row
     *            the row
     * @param start
     *            the start
     * @param end
     *            the end
     */
    public RowReport(String row, int start, int end) {
        this.row = row;
        this.start = start;
        this.end = start == end && start < row.length() ? start + 1 : end;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public String getRow() {
        return row;
    }

    /**
     * Gets the start.
     *
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * Gets the end.
     *
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * Tokenize.
     *
     * @return the list
     */
    public List<RowPart> tokenize() {
        List<RowPart> lines = new ArrayList<>();
        if (start > 0) {
            lines.add(new RowPart(row.substring(0, start), false));
        }
        if (end - start > 0) {
            lines.add(new RowPart(row.substring(start, end), true));
        }
        if (end < row.length()) {
            lines.add(new RowPart(row.substring(end), false));
        }
        return lines;
    }

    /**
     * Gets the printable lines.
     *
     * @return the printable lines
     */
    public List<String> getPrintableLines() {
        List<String> lines = new ArrayList<>();

        List<RowPart> parts = tokenize();

        lines.add(createContentLine(parts));
        lines.add(createFocusLine(parts));

        return lines;
    }

    /**
     * Creates the content line.
     *
     * @param parts
     *            the parts
     *
     * @return the string
     */
    private String createContentLine(List<RowPart> parts) {
        StringBuilder line = new StringBuilder();
        for (RowPart token : parts) {
            line.append(token.getToken());
        }
        return line.toString();
    }

    /**
     * Creates the focus line.
     *
     * @param parts
     *            the parts
     *
     * @return the string
     */
    private String createFocusLine(List<RowPart> parts) {
        StringBuilder line = new StringBuilder();
        boolean placedMarkers = false;
        for (RowPart token : parts) {
            if (token.isHighlight()) {
                line.append(printUnderscoredPart(token));
                placedMarkers = true;
            } else {
                line.append(printEmptyPart(token));
            }
        }
        if (!placedMarkers) { // Essentially only at the end-of-line
            line.append('^');
        }
        return line.toString();
    }

    /**
     * Prints the empty part.
     *
     * @param token
     *            the token
     *
     * @return the string
     */
    private String printEmptyPart(RowPart token) {
        StringBuilder linePart = new StringBuilder();
        for (int i = 0; i < token.getToken().length(); i++) {
            linePart.append(' ');
        }
        return linePart.toString();
    }

    /**
     * Prints the underscored part.
     *
     * @param token
     *            the token
     *
     * @return the string
     */
    private String printUnderscoredPart(RowPart token) {
        StringBuilder linePart = new StringBuilder();
        for (int i = 0; i < token.getToken().length(); i++) {
            if (i == 0 || i == token.getToken().length() - 1) {
                linePart.append('^');
            } else {
                linePart.append('-');
            }
        }
        return linePart.toString();
    }

}
