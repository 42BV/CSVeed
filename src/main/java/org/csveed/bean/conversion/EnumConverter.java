package org.csveed.bean.conversion;

public class EnumConverter<T extends Enum> extends AbstractConverter<Enum> {

    public Class<T> enumClass;

    public EnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T fromString(String text) throws Exception {
        return (T)Enum.valueOf(this.enumClass, text);
    }

    @Override
    public Class getType() {
        return enumClass;
    }

//    @Override
//    public String toString(Charset value) throws Exception {
//        return value != null ? value.name() : "";
//    }

}
