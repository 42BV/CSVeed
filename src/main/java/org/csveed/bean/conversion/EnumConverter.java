/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class EnumConverter.
 *
 * @param <T>
 *            the generic type
 */
public class EnumConverter<T extends Enum> extends AbstractConverter<T> {

    /** The enum class. */
    public final Class<T> enumClass;

    /**
     * Instantiates a new enum converter.
     *
     * @param enumClass
     *            the enum class
     */
    public EnumConverter(Class<T> enumClass) {
        super(enumClass);
        this.enumClass = enumClass;
    }

    @Override
    public T fromString(String text) throws Exception {
        return (T) Enum.valueOf(this.enumClass, text);
    }

    @Override
    public String toString(T value) throws Exception {
        return value.toString();
    }

}
