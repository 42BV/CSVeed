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
