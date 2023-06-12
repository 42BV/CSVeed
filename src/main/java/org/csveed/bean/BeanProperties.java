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
package org.csveed.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.csveed.bean.conversion.Converter;
import org.csveed.bean.conversion.CustomNumberConverter;
import org.csveed.bean.conversion.DateConverter;
import org.csveed.bean.conversion.EnumConverter;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class BeanProperties.
 */
public class BeanProperties implements Iterable<BeanProperty> {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(BeanProperties.class);

    /** The properties. */
    private List<BeanProperty> properties = new ArrayList<>();

    /** The index to property. */
    private Map<Column, BeanProperty> indexToProperty = new TreeMap<>();

    /** The name to property. */
    private Map<Column, BeanProperty> nameToProperty = new TreeMap<>();

    /** The bean class. */
    private Class beanClass;

    /** The header value property. */
    private BeanProperty headerValueProperty;

    /** The header name property. */
    private BeanProperty headerNameProperty;

    /**
     * Instantiates a new bean properties.
     *
     * @param beanClass
     *            the bean class
     */
    public BeanProperties(Class beanClass) {
        this.beanClass = beanClass;
        parseBean(beanClass);
    }

    /**
     * Parses the bean.
     *
     * @param beanClass
     *            the bean class
     */
    private void parseBean(Class beanClass) {
        final BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException err) {
            throw new RuntimeException(err);
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        // Note that we use getDeclaredFields here instead of the PropertyDescriptor order. The order we now
        // use is guaranteed to be the declaration order from JDK 6+, which is exactly what we need.
        for (Field field : beanClass.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyDescriptors, field);
            if (propertyDescriptor == null || propertyDescriptor.getWriteMethod() == null) {
                LOG.info("Skipping {}.{}", beanClass.getName(), field.getName());
                continue;
            }
            addProperty(propertyDescriptor, field);
        }

        if (beanClass.getSuperclass() != null) {
            parseBean(beanClass.getSuperclass());
        }
    }

    /**
     * Adds the property.
     *
     * @param propertyDescriptor
     *            the property descriptor
     * @param field
     *            the field
     */
    @SuppressWarnings("unchecked")
    private void addProperty(PropertyDescriptor propertyDescriptor, Field field) {
        BeanProperty beanProperty = new BeanProperty();
        beanProperty.setPropertyDescriptor(propertyDescriptor);
        beanProperty.setField(field);
        if (Enum.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
            beanProperty.setConverter(new EnumConverter(propertyDescriptor.getPropertyType()));
        }
        this.properties.add(beanProperty);
    }

    /**
     * Gets the property descriptor.
     *
     * @param propertyDescriptors
     *            the property descriptors
     * @param field
     *            the field
     *
     * @return the property descriptor
     */
    private PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, Field field) {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (field.getName().equals(propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    /**
     * Sets the required.
     *
     * @param propertyName
     *            the property name
     * @param required
     *            the required
     */
    public void setRequired(String propertyName, boolean required) {
        get(propertyName).setRequired(required);
    }

    /**
     * Sets the date.
     *
     * @param propertyName
     *            the property name
     * @param formatText
     *            the format text
     */
    public void setDate(String propertyName, String formatText) {
        setConverter(propertyName, new DateConverter(formatText, true));
    }

    /**
     * Sets the localized number.
     *
     * @param propertyName
     *            the property name
     * @param locale
     *            the locale
     */
    public void setLocalizedNumber(String propertyName, Locale locale) {
        Class<? extends Number> numberClass = get(propertyName).getNumberClass();
        if (numberClass == null) {
            throw new CsvException(new GeneralError(
                    "Property " + beanClass.getName() + "." + propertyName + " is not a java.lang.Number"));
        }
        CustomNumberConverter converter = new CustomNumberConverter(numberClass, NumberFormat.getNumberInstance(locale),
                true);
        setConverter(propertyName, converter);
    }

    /**
     * Sets the converter.
     *
     * @param propertyName
     *            the property name
     * @param converter
     *            the converter
     */
    public void setConverter(String propertyName, Converter converter) {
        get(propertyName).setConverter(converter);
    }

    /**
     * Removes the from column index.
     *
     * @param property
     *            the property
     */
    protected void removeFromColumnIndex(BeanProperty property) {
        while (indexToProperty.values().remove(property)) {

        }
    }

    /**
     * Removes the from column name.
     *
     * @param property
     *            the property
     */
    protected void removeFromColumnName(BeanProperty property) {
        while (nameToProperty.values().remove(property)) {

        }
    }

    /**
     * Ignore property.
     *
     * @param propertyName
     *            the property name
     */
    public void ignoreProperty(String propertyName) {
        BeanProperty property = get(propertyName);
        properties.remove(property);
        removeFromColumnIndex(property);
        removeFromColumnName(property);
    }

    /**
     * Sets the header value property.
     *
     * @param propertyName
     *            the new header value property
     */
    public void setHeaderValueProperty(String propertyName) {
        this.headerValueProperty = get(propertyName);
        this.headerValueProperty.setDynamicColumnProperty(true);
    }

    /**
     * Sets the header name property.
     *
     * @param propertyName
     *            the new header name property
     */
    public void setHeaderNameProperty(String propertyName) {
        this.headerNameProperty = get(propertyName);
        this.headerNameProperty.setDynamicColumnProperty(true);
    }

    /**
     * Gets the header value property.
     *
     * @return the header value property
     */
    public BeanProperty getHeaderValueProperty() {
        return this.headerValueProperty;
    }

    /**
     * Gets the header name property.
     *
     * @return the header name property
     */
    public BeanProperty getHeaderNameProperty() {
        return this.headerNameProperty;
    }

    /**
     * Map index to property.
     *
     * @param columnIndex
     *            the column index
     * @param propertyName
     *            the property name
     */
    public void mapIndexToProperty(int columnIndex, String propertyName) {
        BeanProperty property = get(propertyName);
        removeFromColumnIndex(property);
        property.setColumnIndex(columnIndex);
        indexToProperty.put(new Column(columnIndex), property);
    }

    /**
     * Map name to property.
     *
     * @param columnName
     *            the column name
     * @param propertyName
     *            the property name
     */
    public void mapNameToProperty(String columnName, String propertyName) {
        BeanProperty property = get(propertyName);
        removeFromColumnName(property);
        property.setColumnName(columnName);
        nameToProperty.put(new Column(columnName.toLowerCase(Locale.getDefault())), property);
    }

    /**
     * Gets the.
     *
     * @param propertyName
     *            the property name
     *
     * @return the bean property
     */
    protected BeanProperty get(String propertyName) {
        for (BeanProperty beanProperty : properties) {
            if (beanProperty.getPropertyName().equals(propertyName)) {
                return beanProperty;
            }
        }
        throw new CsvException(
                new GeneralError("Property does not exist: " + beanClass.getName() + "." + propertyName));
    }

    /**
     * From index.
     *
     * @param column
     *            the column
     *
     * @return the bean property
     */
    public BeanProperty fromIndex(Column column) {
        return indexToProperty.get(column);
    }

    /**
     * From name.
     *
     * @param column
     *            the column
     *
     * @return the bean property
     */
    public BeanProperty fromName(Column column) {
        return nameToProperty.get(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<BeanProperty> iterator() {
        return ((List<BeanProperty>) ((ArrayList<BeanProperty>) this.properties).clone()).iterator();
    }

    /**
     * Column index keys.
     *
     * @return the sets the
     */
    public Set<Column> columnIndexKeys() {
        return this.indexToProperty.keySet();
    }

    /**
     * Column name keys.
     *
     * @return the sets the
     */
    public Set<Column> columnNameKeys() {
        return this.nameToProperty.keySet();
    }

}
