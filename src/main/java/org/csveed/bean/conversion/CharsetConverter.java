/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import java.nio.charset.Charset;

/**
 * The Class CharsetConverter.
 */
public class CharsetConverter extends AbstractConverter<Charset> {

    /**
     * Instantiates a new charset converter.
     */
    public CharsetConverter() {
        super(Charset.class);
    }

    @Override
    public Charset fromString(String text) throws Exception {
        if (text != null && !text.isEmpty()) {
            return Charset.forName(text);
        }
        return null;
    }

    @Override
    public String toString(Charset value) throws Exception {
        return value != null ? value.name() : "";
    }

}
