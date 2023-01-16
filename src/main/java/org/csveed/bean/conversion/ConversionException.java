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

public abstract class ConversionException extends Exception {

    private static final long serialVersionUID = 1L;

    private String typeDescription;

    public ConversionException(String message, Class clazz) {
        this(message, clazz.getName(), null);
    }

    public ConversionException(String message, String typeDescription, Throwable exception) {
        super(message, exception);
        this.typeDescription = typeDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}
