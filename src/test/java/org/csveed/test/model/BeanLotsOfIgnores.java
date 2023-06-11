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

import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;

/**
 * The Class BeanLotsOfIgnores.
 */
@CsvFile(useHeader = false)
public class BeanLotsOfIgnores {

    /** The take this 1. */
    private Integer takeThis1;

    /** The leave that 1. */
    @CsvIgnore
    private Integer leaveThat1;

    /** The pick this 1. */
    private Integer pickThis1;

    /** The ditch that 1. */
    @CsvIgnore
    private Integer ditchThat1;

    /** The choose this 1. */
    private Integer chooseThis1;

    /**
     * Gets the take this 1.
     *
     * @return the take this 1
     */
    public Integer getTakeThis1() {
        return takeThis1;
    }

    /**
     * Sets the take this 1.
     *
     * @param takeThis1
     *            the new take this 1
     */
    public void setTakeThis1(Integer takeThis1) {
        this.takeThis1 = takeThis1;
    }

    /**
     * Gets the leave that 1.
     *
     * @return the leave that 1
     */
    public Integer getLeaveThat1() {
        return leaveThat1;
    }

    /**
     * Sets the leave that 1.
     *
     * @param leaveThat1
     *            the new leave that 1
     */
    public void setLeaveThat1(Integer leaveThat1) {
        this.leaveThat1 = leaveThat1;
    }

    /**
     * Gets the pick this 1.
     *
     * @return the pick this 1
     */
    public Integer getPickThis1() {
        return pickThis1;
    }

    /**
     * Sets the pick this 1.
     *
     * @param pickThis1
     *            the new pick this 1
     */
    public void setPickThis1(Integer pickThis1) {
        this.pickThis1 = pickThis1;
    }

    /**
     * Gets the ditch that 1.
     *
     * @return the ditch that 1
     */
    public Integer getDitchThat1() {
        return ditchThat1;
    }

    /**
     * Sets the ditch that 1.
     *
     * @param ditchThat1
     *            the new ditch that 1
     */
    public void setDitchThat1(Integer ditchThat1) {
        this.ditchThat1 = ditchThat1;
    }

    /**
     * Gets the choose this 1.
     *
     * @return the choose this 1
     */
    public Integer getChooseThis1() {
        return chooseThis1;
    }

    /**
     * Sets the choose this 1.
     *
     * @param chooseThis1
     *            the new choose this 1
     */
    public void setChooseThis1(Integer chooseThis1) {
        this.chooseThis1 = chooseThis1;
    }
}
