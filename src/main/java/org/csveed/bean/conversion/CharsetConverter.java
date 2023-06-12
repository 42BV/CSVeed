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
