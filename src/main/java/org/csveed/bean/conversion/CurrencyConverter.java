/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
