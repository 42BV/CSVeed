package org.csveed.bean.conversion;

public class CharArrayConverter extends AbstractConverter<char[]> {

    @Override
    public char[] fromString(String text) throws Exception {
        return text != null ? text.toCharArray() : null;
    }

    @Override
    public Class getType() {
        return char[].class;
    }

//    @Override
//    public String toString(char[] value) throws Exception {
//        return value != null ? new String(value) : "";
//    }
}
