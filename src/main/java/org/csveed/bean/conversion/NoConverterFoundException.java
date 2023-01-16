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

public class NoConverterFoundException extends ConversionException {

    private static final long serialVersionUID = 1L;

    public NoConverterFoundException(String message, Class propertyType) {
        super(message, propertyType);
    }

}
