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
