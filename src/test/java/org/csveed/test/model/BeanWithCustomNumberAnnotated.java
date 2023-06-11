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

import org.csveed.annotations.CsvLocalizedNumber;

/**
 * The Class BeanWithCustomNumberAnnotated.
 */
public class BeanWithCustomNumberAnnotated {

    /** The number. */
    @CsvLocalizedNumber(language = "de", country = "DE")
    private Double number;

    /**
     * Gets the number.
     *
     * @return the number
     */
    public Double getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number
     *            the new number
     */
    public void setNumber(Double number) {
        this.number = number;
    }
}
