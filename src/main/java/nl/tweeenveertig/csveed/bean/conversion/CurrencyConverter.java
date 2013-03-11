package nl.tweeenveertig.csveed.bean.conversion;

import java.util.Currency;

public class CurrencyConverter implements Converter<Currency> {

    @Override
    public Currency fromString(String text) {
        return Currency.getInstance(text);
    }

    @Override
    public String toString(Currency value) {
        return (value != null ? value.getCurrencyCode() : "");
    }

}
