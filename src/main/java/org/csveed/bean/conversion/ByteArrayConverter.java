package org.csveed.bean.conversion;

public class ByteArrayConverter extends AbstractConverter<byte[]> {

    @Override
    public byte[] fromString(String text) throws Exception {
        return text != null ? text.getBytes() : null;
    }

    @Override
    public Class getType() {
        return byte[].class;
    }

//    @Override
//    public String toString(byte[] value) throws Exception {
//        return value != null ? new String(value) : "";
//    }
}
