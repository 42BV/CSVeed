/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
