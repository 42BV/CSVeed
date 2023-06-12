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
 * The Class NoConverterFoundException.
 */
public class NoConverterFoundException extends ConversionException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new no converter found exception.
     *
     * @param message
     *            the message
     * @param propertyType
     *            the property type
     */
    public NoConverterFoundException(String message, Class propertyType) {
        super(message, propertyType);
    }

}
