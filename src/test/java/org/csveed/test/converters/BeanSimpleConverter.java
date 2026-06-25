/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.converters;

import org.csveed.bean.conversion.AbstractConverter;
import org.csveed.test.model.BeanSimple;

/**
 * The Class BeanSimpleConverter.
 */
public class BeanSimpleConverter extends AbstractConverter<BeanSimple> {

    /**
     * Instantiates a new bean simple converter.
     */
    public BeanSimpleConverter() {
        super(BeanSimple.class);
    }

    @Override
    public BeanSimple fromString(String text) throws Exception {
        BeanSimple bean = new BeanSimple();
        bean.setName(text);
        return bean;
    }

    @Override
    public String toString(BeanSimple value) throws Exception {
        return value.getName();
    }

}
