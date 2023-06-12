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
