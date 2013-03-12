package nl.tweeenveertig.csveed.bean.conversion;

import nl.tweeenveertig.csveed.bean.BeanProperty;

import java.lang.reflect.Method;

public class BeanWrapper {

    private DefaultConverters defaultConverters;

    private Object bean;

    public BeanWrapper(DefaultConverters defaultConverters, Object bean) {
        this.defaultConverters = defaultConverters;
        this.bean = bean;
    }

    public void setProperty(BeanProperty property, String value) throws Exception {
        Method writeMethod = property.getPropertyDescriptor().getWriteMethod();
        Converter converter = getConverter(property);
        writeMethod.invoke(bean, converter.fromString(value));
    }

    protected Converter getConverter(BeanProperty property) {
        return defaultConverters.getConverter(property.getPropertyDescriptor().getPropertyType());
    }

}
