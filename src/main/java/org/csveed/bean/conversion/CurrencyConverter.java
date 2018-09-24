package org.csveed.bean.conversion;

import java.util.Currency;

public class CurrencyConverter extends AbstractConverter<Currency> {

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
