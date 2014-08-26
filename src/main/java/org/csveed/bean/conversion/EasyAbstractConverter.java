package org.csveed.bean.conversion;

public abstract class EasyAbstractConverter<K> extends AbstractConverter<K> {

    public EasyAbstractConverter(Class<K> clazz) {
        super(clazz);
    }

    @Override
    public String toString(K value) throws Exception {
        return null;
    }

}
