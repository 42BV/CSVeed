/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class ConversionUtil.
 */
public final class ConversionUtil {

    /**
     * Instantiates a new conversion util.
     */
    private ConversionUtil() {
        // Prevent Instantiation of static utils class
    }

    /**
     * Checks for length.
     *
     * @param str
     *            the str
     *
     * @return true, if successful
     */
    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    /**
     * Checks for text.
     *
     * @param str
     *            the str
     *
     * @return true, if successful
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * Checks for text.
     *
     * @param str
     *            the str
     *
     * @return true, if successful
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trim all whitespace.
     *
     * @param str
     *            the str
     *
     * @return the string
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

}
