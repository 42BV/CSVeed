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
