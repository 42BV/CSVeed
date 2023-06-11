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

import java.nio.charset.Charset;

public class ByteArrayConverter extends AbstractConverter<byte[]> {

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
