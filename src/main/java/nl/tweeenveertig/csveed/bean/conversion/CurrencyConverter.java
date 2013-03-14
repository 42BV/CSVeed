package nl.tweeenveertig.csveed.bean.conversion;

import java.util.Currency;

public class CurrencyConverter extends AbstractConverter<Currency> {

    @Override
    public Currency fromString(String text) {
        return Currency.getInstance(text);
    }

    @Override
    public Class getType() {
        return Currency.class;
    }

//    @Override
//    public String toString(Currency value) {
//        return (value != null ? value.getCurrencyCode() : "");
//    }

}
