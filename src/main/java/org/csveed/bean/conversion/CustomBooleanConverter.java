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

import static org.csveed.bean.conversion.ConversionUtil.hasLength;

/**
 * The Class CustomBooleanConverter.
 */
public class CustomBooleanConverter extends AbstractConverter<Boolean> {

    /** The Constant VALUE_TRUE. */
    public static final String VALUE_TRUE = "true";

    /** The Constant VALUE_FALSE. */
    public static final String VALUE_FALSE = "false";

    /** The Constant VALUE_SHORT_TRUE. */
    public static final String VALUE_SHORT_TRUE = "T";

    /** The Constant VALUE_SHORT_FALSE. */
    public static final String VALUE_SHORT_FALSE = "F";

    /** The Constant VALUE_ON. */
    public static final String VALUE_ON = "on";

    /** The Constant VALUE_OFF. */
    public static final String VALUE_OFF = "off";

    /** The Constant VALUE_YES. */
    public static final String VALUE_YES = "yes";

    /** The Constant VALUE_NO. */
    public static final String VALUE_NO = "no";

    /** The Constant VALUE_SHORT_YES. */
    public static final String VALUE_SHORT_YES = "Y";

    /** The Constant VALUE_SHORT_NO. */
    public static final String VALUE_SHORT_NO = "N";

    /** The Constant VALUE_1. */
    public static final String VALUE_1 = "1";

    /** The Constant VALUE_0. */
    public static final String VALUE_0 = "0";

    /** The true string. */
    private final String trueString;

    /** The false string. */
    private final String falseString;

    /** The allow empty. */
    private final boolean allowEmpty;

    /**
     * Instantiates a new custom boolean converter.
     *
     * @param allowEmpty
     *            the allow empty
     */
    public CustomBooleanConverter(boolean allowEmpty) {
        this(null, null, allowEmpty);
    }

    /**
     * Instantiates a new custom boolean converter.
     *
     * @param trueString
     *            the true string
     * @param falseString
     *            the false string
     * @param allowEmpty
     *            the allow empty
     */
    public CustomBooleanConverter(String trueString, String falseString, boolean allowEmpty) {
        super(Boolean.class);
        this.trueString = trueString;
        this.falseString = falseString;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Boolean fromString(String text) throws Exception {
        String input = text != null ? text.trim() : null;
        if (this.allowEmpty && !hasLength(input)) {
            return null;
        }
        if (this.trueString != null && this.trueString.equalsIgnoreCase(input)) {
            return Boolean.TRUE;
        }
        if (this.falseString != null && this.falseString.equalsIgnoreCase(input)) {
            return Boolean.FALSE;
        }
        if (this.trueString == null && (VALUE_TRUE.equalsIgnoreCase(input) || VALUE_SHORT_TRUE.equalsIgnoreCase(input)
                || VALUE_ON.equalsIgnoreCase(input) || VALUE_YES.equalsIgnoreCase(input)
                || VALUE_SHORT_YES.equalsIgnoreCase(input) || VALUE_1.equals(input))) {
            return Boolean.TRUE;
        }
        if (this.falseString == null
                && (VALUE_FALSE.equalsIgnoreCase(input) || VALUE_SHORT_FALSE.equalsIgnoreCase(input)
                        || VALUE_OFF.equalsIgnoreCase(input) || VALUE_NO.equalsIgnoreCase(input)
                        || VALUE_SHORT_NO.equalsIgnoreCase(input) || VALUE_0.equals(input))) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
    }

    @Override
    public String toString(Boolean value) throws Exception {
        if (Boolean.TRUE.equals(value)) {
            return this.trueString != null ? this.trueString : VALUE_TRUE;
        }
        if (Boolean.FALSE.equals(value)) {
            return this.falseString != null ? this.falseString : VALUE_FALSE;
        }
        return "";
    }

}
