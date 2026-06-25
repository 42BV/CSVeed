/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import java.nio.charset.Charset;

/**
 * The Class ByteArrayConverter.
 */
public class ByteArrayConverter extends AbstractConverter<byte[]> {

    /**
     * Instantiates a new byte array converter.
     */
    public ByteArrayConverter() {
        super(byte[].class);
    }

    @Override
    public byte[] fromString(String text) throws Exception {
        return text != null ? text.getBytes(Charset.defaultCharset()) : null;
    }

    @Override
    public String toString(byte[] value) throws Exception {
        return value != null ? new String(value, Charset.defaultCharset()) : "";
    }
}
