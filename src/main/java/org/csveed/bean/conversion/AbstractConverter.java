package org.csveed.bean.conversion;

public abstract class AbstractConverter<K> implements Converter<K> {

    public String infoOnType() {
        return getType().getName();
    }

}
