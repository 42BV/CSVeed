/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
