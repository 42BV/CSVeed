/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import static org.csveed.bean.conversion.ConversionUtil.hasText;

import java.text.NumberFormat;

/**
 * The Class CustomNumberConverter.
 */
public class CustomNumberConverter extends AbstractConverter<Number> {

    /** The number class. */
    private final Class<? extends Number> numberClass;

    /** The number format. */
    private final NumberFormat numberFormat;

    /** The allow empty. */
    private final boolean allowEmpty;

    /**
     * Instantiates a new custom number converter.
     *
     * @param numberClass
     *            the number class
     * @param allowEmpty
     *            the allow empty
     *
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    public CustomNumberConverter(Class<? extends Number> numberClass, boolean allowEmpty)
            throws IllegalArgumentException {
        this(numberClass, null, allowEmpty);
    }

    /**
     * Instantiates a new custom number converter.
     *
     * @param numberClass
     *            the number class
     * @param numberFormat
     *            the number format
     * @param allowEmpty
     *            the allow empty
     *
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    public CustomNumberConverter(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty)
            throws IllegalArgumentException {
        super(Number.class);

        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)) {
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Number fromString(String text) throws Exception {
        if (this.allowEmpty && !hasText(text)) {
            return null;
        }
        if (this.numberFormat != null) {
            return determineValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        }
        return determineValue(NumberUtils.parseNumber(text, this.numberClass));
    }

    /**
     * Determine value.
     *
     * @param value
     *            the value
     *
     * @return the number
     */
    public Number determineValue(Object value) {
        if (value instanceof Number) {
            return NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass);
        }
        return null;
    }

    @Override
    public String toString(Number value) throws Exception {
        if (value == null) {
            return "";
        }
        if (this.numberFormat != null) {
            return this.numberFormat.format(value);
        }
        return value.toString();
    }

}
