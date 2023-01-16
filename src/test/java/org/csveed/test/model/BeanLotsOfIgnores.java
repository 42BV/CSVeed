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

@CsvFile(useHeader = false)
public class BeanLotsOfIgnores {

    private Integer takeThis1;

    @CsvIgnore
    private Integer leaveThat1;

    private Integer pickThis1;

    @CsvIgnore
    private Integer ditchThat1;

    private Integer chooseThis1;

    public Integer getTakeThis1() {
        return takeThis1;
    }

    public void setTakeThis1(Integer takeThis1) {
        this.takeThis1 = takeThis1;
    }

    public Integer getLeaveThat1() {
        return leaveThat1;
    }

    public void setLeaveThat1(Integer leaveThat1) {
        this.leaveThat1 = leaveThat1;
    }

    public Integer getPickThis1() {
        return pickThis1;
    }

    public void setPickThis1(Integer pickThis1) {
        this.pickThis1 = pickThis1;
    }

    public Integer getDitchThat1() {
        return ditchThat1;
    }

    public void setDitchThat1(Integer ditchThat1) {
        this.ditchThat1 = ditchThat1;
    }

    public Integer getChooseThis1() {
        return chooseThis1;
    }

    public void setChooseThis1(Integer chooseThis1) {
        this.chooseThis1 = chooseThis1;
    }
}
