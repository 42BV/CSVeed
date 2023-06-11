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
package org.csveed.test.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvFile;

/**
 * The Class BeanWithCustomIndexes.
 */
@CsvFile(useHeader = false)
public class BeanWithCustomIndexes {

    /** The line 3. */
    @CsvCell(columnIndex = 5)
    private String line3;

    /** The line 0. */
    @CsvCell(columnIndex = 2)
    private String line0;

    /** The line 1. */
    @CsvCell(columnIndex = 3)
    private String line1;

    /** The line 2. */
    @CsvCell(columnIndex = 4)
    private String line2;

    /**
     * Gets the line 3.
     *
     * @return the line 3
     */
    public String getLine3() {
        return line3;
    }

    /**
     * Sets the line 3.
     *
     * @param line3
     *            the new line 3
     */
    public void setLine3(String line3) {
        this.line3 = line3;
    }

    /**
     * Gets the line 0.
     *
     * @return the line 0
     */
    public String getLine0() {
        return line0;
    }

    /**
     * Sets the line 0.
     *
     * @param line0
     *            the new line 0
     */
    public void setLine0(String line0) {
        this.line0 = line0;
    }

    /**
     * Gets the line 1.
     *
     * @return the line 1
     */
    public String getLine1() {
        return line1;
    }

    /**
     * Sets the line 1.
     *
     * @param line1
     *            the new line 1
     */
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    /**
     * Gets the line 2.
     *
     * @return the line 2
     */
    public String getLine2() {
        return line2;
    }

    /**
     * Sets the line 2.
     *
     * @param line2
     *            the new line 2
     */
    public void setLine2(String line2) {
        this.line2 = line2;
    }
}
