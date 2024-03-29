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
package org.csveed.bean.conversion;

/**
 * The Class AbstractConverter.
 *
 * @param <K>
 *            the key type
 */
public abstract class AbstractConverter<K> implements Converter<K> {

    /** The clazz. */
    private Class<K> clazz;

    /**
     * Instantiates a new abstract converter.
     *
     * @param clazz
     *            the clazz
     */
    protected AbstractConverter(Class<K> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String infoOnType() {
        return getType().getName();
    }

    @Override
    public Class<K> getType() {
        return clazz;
    }

}
