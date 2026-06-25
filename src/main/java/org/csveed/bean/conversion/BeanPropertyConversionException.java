/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
