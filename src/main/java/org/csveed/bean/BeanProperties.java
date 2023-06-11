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

public class BeanProperties implements Iterable<BeanProperty> {

    private static final Logger LOG = LoggerFactory.getLogger(BeanProperties.class);

    private List<BeanProperty> properties = new ArrayList<>();

    private Map<Column, BeanProperty> indexToProperty = new TreeMap<>();
    private Map<Column, BeanProperty> nameToProperty = new TreeMap<>();

    private Class beanClass;

    private BeanProperty headerValueProperty;
    private BeanProperty headerNameProperty;

    public BeanProperties(Class beanClass) {
        this.beanClass = beanClass;
        parseBean(beanClass);
    }

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

    private PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, Field field) {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (field.getName().equals(propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    public void setRequired(String propertyName, boolean required) {
        get(propertyName).setRequired(required);
    }

    public void setDate(String propertyName, String formatText) {
        setConverter(propertyName, new DateConverter(formatText, true));
    }

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

    public void setConverter(String propertyName, Converter converter) {
        get(propertyName).setConverter(converter);
    }

    protected void removeFromColumnIndex(BeanProperty property) {
        while (indexToProperty.values().remove(property)) {
            ;
        }
    }

    protected void removeFromColumnName(BeanProperty property) {
        while (nameToProperty.values().remove(property)) {
            ;
        }
    }

    public void ignoreProperty(String propertyName) {
        BeanProperty property = get(propertyName);
        properties.remove(property);
        removeFromColumnIndex(property);
        removeFromColumnName(property);
    }

    public void setHeaderValueProperty(String propertyName) {
        this.headerValueProperty = get(propertyName);
        this.headerValueProperty.setDynamicColumnProperty(true);
    }

    public void setHeaderNameProperty(String propertyName) {
        this.headerNameProperty = get(propertyName);
        this.headerNameProperty.setDynamicColumnProperty(true);
    }

    public BeanProperty getHeaderValueProperty() {
        return this.headerValueProperty;
    }

    public BeanProperty getHeaderNameProperty() {
        return this.headerNameProperty;
    }

    public void mapIndexToProperty(int columnIndex, String propertyName) {
        BeanProperty property = get(propertyName);
        removeFromColumnIndex(property);
        property.setColumnIndex(columnIndex);
        indexToProperty.put(new Column(columnIndex), property);
    }

    public void mapNameToProperty(String columnName, String propertyName) {
        BeanProperty property = get(propertyName);
        removeFromColumnName(property);
        property.setColumnName(columnName);
        nameToProperty.put(new Column(columnName.toLowerCase(Locale.getDefault())), property);
    }

    protected BeanProperty get(String propertyName) {
        for (BeanProperty beanProperty : properties) {
            if (beanProperty.getPropertyName().equals(propertyName)) {
                return beanProperty;
            }
        }
        throw new CsvException(
                new GeneralError("Property does not exist: " + beanClass.getName() + "." + propertyName));
    }

    public BeanProperty fromIndex(Column column) {
        return indexToProperty.get(column);
    }

    public BeanProperty fromName(Column column) {
        return nameToProperty.get(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<BeanProperty> iterator() {
        return ((List<BeanProperty>) ((ArrayList<BeanProperty>) this.properties).clone()).iterator();
    }

    public Set<Column> columnIndexKeys() {
        return this.indexToProperty.keySet();
    }

    public Set<Column> columnNameKeys() {
        return this.nameToProperty.keySet();
    }

}
