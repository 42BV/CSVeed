package org.csveed.bean.conversion;

public abstract class AbstractConverter<K> implements Converter<K> {

    private Class<K> clazz;

    public AbstractConverter(Class<K> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String infoOnType() {
        return getType().getName();
    }

    @Override
    public Class<K> getType() {
        return clazz;
    }

}
