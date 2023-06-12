/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.bean.conversion;

import java.lang.reflect.Method;

import org.csveed.bean.BeanProperty;

/**
 * The Class BeanWrapper.
 */
public class BeanWrapper {

    /** The default converters. */
    private DefaultConverters defaultConverters;

    /** The bean. */
    private Object bean;

    /**
     * Instantiates a new bean wrapper.
     *
     * @param defaultConverters
     *            the default converters
     * @param bean
     *            the bean
     */
    public BeanWrapper(DefaultConverters defaultConverters, Object bean) {
        this.defaultConverters = defaultConverters;
        this.bean = bean;
    }

    /**
     * Gets the property.
     *
     * @param property
     *            the property
     *
     * @return the property
     *
     * @throws ConversionException
     *             the conversion exception
     */
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

    /**
     * Sets the property.
     *
     * @param property
     *            the property
     * @param value
     *            the value
     *
     * @throws ConversionException
     *             the conversion exception
     */
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

    /**
     * Gets the converter.
     *
     * @param property
     *            the property
     *
     * @return the converter
     */
    protected Converter getConverter(BeanProperty property) {
        if (property.getConverter() != null) {
            return property.getConverter();
        }
        return defaultConverters.getConverter(getPropertyType(property));
    }

    /**
     * Gets the property type.
     *
     * @param property
     *            the property
     *
     * @return the property type
     */
    protected Class getPropertyType(BeanProperty property) {
        return property.getPropertyDescriptor().getPropertyType();
    }

}
