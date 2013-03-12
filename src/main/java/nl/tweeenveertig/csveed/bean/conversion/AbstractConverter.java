package nl.tweeenveertig.csveed.bean.conversion;

public abstract class AbstractConverter<K> implements Converter<K> {

    public String getType(Class clazz) {
        return clazz.getName();
    }

}
