/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
