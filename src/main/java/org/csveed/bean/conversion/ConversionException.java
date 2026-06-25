/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class ConversionException.
 */
public abstract class ConversionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The type description. */
    private String typeDescription;

    /**
     * Instantiates a new conversion exception.
     *
     * @param message
     *            the message
     * @param clazz
     *            the clazz
     */
    protected ConversionException(String message, Class clazz) {
        this(message, clazz.getName(), null);
    }

    /**
     * Instantiates a new conversion exception.
     *
     * @param message
     *            the message
     * @param typeDescription
     *            the type description
     * @param exception
     *            the exception
     */
    protected ConversionException(String message, String typeDescription, Throwable exception) {
        super(message, exception);
        this.typeDescription = typeDescription;
    }

    /**
     * Gets the type description.
     *
     * @return the type description
     */
    public String getTypeDescription() {
        return typeDescription;
    }
}
