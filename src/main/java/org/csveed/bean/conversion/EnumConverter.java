package org.csveed.bean.conversion;

public class EnumConverter<T extends Enum> extends AbstractConverter<T> {

    public Class<T> enumClass;

    public EnumConverter(Class<T> enumClass) {
        super(enumClass);
        this.enumClass = enumClass;
    }

    @Override
    public T fromString(String text) throws Exception {
        return (T) Enum.valueOf(this.enumClass, text);
    }

    @Override
    public String toString(T value) throws Exception {
        return value.toString();
    }

}
