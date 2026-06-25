/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class CharArrayConverter.
 */
public class CharArrayConverter extends AbstractConverter<char[]> {

    /**
     * Instantiates a new char array converter.
     */
    public CharArrayConverter() {
        super(char[].class);
    }

    @Override
    public char[] fromString(String text) throws Exception {
        return text != null ? text.toCharArray() : null;
    }

    @Override
    public String toString(char[] value) throws Exception {
        return value != null ? new String(value) : "";
    }

}
