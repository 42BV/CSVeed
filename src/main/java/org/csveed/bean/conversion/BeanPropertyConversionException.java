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
 * The Class BeanPropertyConversionException.
 */
public class BeanPropertyConversionException extends ConversionException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new bean property conversion exception.
     *
     * @param message
     *            the message
     * @param propertyDescription
     *            the property description
     * @param exception
     *            the exception
     */
    public BeanPropertyConversionException(String message, String propertyDescription, Throwable exception) {
        super(message, propertyDescription, exception);
    }

}
