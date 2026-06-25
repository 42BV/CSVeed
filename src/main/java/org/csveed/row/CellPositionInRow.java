/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.row;

/**
 * The Class CellPositionInRow.
 */
public class CellPositionInRow {

    /** The start. */
    private int start;

    /** The end. */
    private int end;

    /**
     * Gets the start.
     *
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the start.
     *
     * @param start
     *            the new start
     */
    public void setStart(int start) {
        this.start = start;
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
     * Sets the end.
     *
     * @param end
     *            the new end
     */
    public void setEnd(int end) {
        this.end = end;
    }
}
