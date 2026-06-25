/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.model;

import org.csveed.annotations.CsvFile;

/**
 * The Class BeanWithoutNoArgPublicConstructor.
 */
@CsvFile(useHeader = false)
public class BeanWithoutNoArgPublicConstructor {

    /** The name. */
    private String name;

    /**
     * Instantiates a new bean without no arg public constructor.
     *
     * @param name
     *            the name
     */
    public BeanWithoutNoArgPublicConstructor(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

}
