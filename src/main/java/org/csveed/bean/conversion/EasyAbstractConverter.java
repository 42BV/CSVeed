/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class EasyAbstractConverter.
 *
 * @param <K>
 *            the key type
 */
public abstract class EasyAbstractConverter<K> extends AbstractConverter<K> {

    /**
     * Instantiates a new easy abstract converter.
     *
     * @param clazz
     *            the clazz
     */
    protected EasyAbstractConverter(Class<K> clazz) {
        super(clazz);
    }

    @Override
    public String toString(K value) throws Exception {
        return null;
    }

}
