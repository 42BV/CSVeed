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

import java.util.Currency;

/**
 * The Class CurrencyConverter.
 */
public class CurrencyConverter extends AbstractConverter<Currency> {

    /**
     * Instantiates a new currency converter.
     */
    public CurrencyConverter() {
        super(Currency.class);
    }

    @Override
    public Currency fromString(String text) {
        return Currency.getInstance(text);
    }

    @Override
    public String toString(Currency value) {
        return value != null ? value.getCurrencyCode() : "";
    }

}
