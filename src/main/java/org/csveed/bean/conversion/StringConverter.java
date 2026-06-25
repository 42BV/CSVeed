/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * The Class StringConverter.
 */
public class StringConverter extends AbstractConverter<String> {

    /**
     * Instantiates a new string converter.
     */
    public StringConverter() {
        super(String.class);
    }

    @Override
    public String fromString(String text) {
        return text;
    }

    @Override
    public String toString(String value) {
        return value;
    }

}
