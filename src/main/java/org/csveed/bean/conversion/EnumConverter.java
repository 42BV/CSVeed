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
