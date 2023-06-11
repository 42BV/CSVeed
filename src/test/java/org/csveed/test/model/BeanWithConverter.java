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

import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvFile;
import org.csveed.test.converters.BeanSimpleConverter;

/**
 * The Class BeanWithConverter.
 */
@CsvFile(useHeader = false)
public class BeanWithConverter {

    /** The bean. */
    @CsvConverter(converter = BeanSimpleConverter.class)
    private BeanSimple bean;

    /**
     * Gets the bean.
     *
     * @return the bean
     */
    public BeanSimple getBean() {
        return bean;
    }

    /**
     * Sets the bean.
     *
     * @param bean
     *            the new bean
     */
    public void setBean(BeanSimple bean) {
        this.bean = bean;
    }
}
