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
