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

    public void setProperty(BeanProperty property, String value) throws ConversionException {
        Method writeMethod = property.getPropertyDescriptor().getWriteMethod();
        Converter converter = getConverter(property);
        if (converter == null) {
            throw new NoConverterFoundException("No Converter found for", getPropertyType(property));
        }
        try {
            writeMethod.invoke(bean, converter.fromString(value));
        } catch (Exception err) {
            throw new BeanPropertyConversionException("Problem converting", converter.infoOnType(), err);
        }
    }

    protected Converter getConverter(BeanProperty property) {
        if (property.getConverter() != null) {
            return property.getConverter();
        } else {
            return defaultConverters.getConverter(getPropertyType(property));
        }
    }

    protected Class getPropertyType(BeanProperty property) {
        return property.getPropertyDescriptor().getPropertyType();
    }

}
