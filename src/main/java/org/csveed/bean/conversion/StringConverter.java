package org.csveed.bean.conversion;

public class StringConverter extends AbstractConverter<String> {

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
