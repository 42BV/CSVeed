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

public class CharArrayConverter extends AbstractConverter<char[]> {

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
