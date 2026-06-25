/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
