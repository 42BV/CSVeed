package org.csveed.bean.conversion;

import java.lang.reflect.Method;

import org.csveed.bean.BeanProperty;

public class BeanWrapper {

    private DefaultConverters defaultConverters;

    private Object bean;

    public BeanWrapper(DefaultConverters defaultConverters, Object bean) {
        this.defaultConverters = defaultConverters;
        this.bean = bean;
    }

    public String getProperty(BeanProperty property) throws ConversionException {
        Method readMethod = property.getPropertyDescriptor().getReadMethod();
        Converter converter = getConverter(property);
        if (converter == null) {
            throw new NoConverterFoundException("No Converter found for", getPropertyType(property));
        }
        try {
            Object value = readMethod.invoke(bean);
            return converter.toString(value);
        } catch (Exception err) {
            throw new BeanPropertyConversionException("Problem converting", converter.infoOnType(), err);
        }
    }

    public void setProperty(BeanProperty property, String value) throws ConversionException {
        Method writeMethod = property.getPropertyDescriptor().getWriteMethod();
        Converter converter = getConverter(property);
        if (converter == null) {
            throw new NoConverterFoundException("No Converter found for", getPropertyType(property));
        }
        try {
            writeMethod.invoke(bean, value == null || value.equals("") ? null : converter.fromString(value));
        } catch (Exception err) {
            throw new BeanPropertyConversionException("Problem converting", converter.infoOnType(), err);
        }
    }

    protected Converter getConverter(BeanProperty property) {
        if (property.getConverter() != null) {
            return property.getConverter();
        }
        return defaultConverters.getConverter(getPropertyType(property));
    }

    protected Class getPropertyType(BeanProperty property) {
        return property.getPropertyDescriptor().getPropertyType();
    }

}
