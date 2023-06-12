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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.csveed.bean.conversion.Converter;

/**
 * The Class BeanProperty.
 */
public class BeanProperty {

    /** The property descriptor. */
    private PropertyDescriptor propertyDescriptor;

    /** The field. */
    private Field field;

    /** The converter. */
    private Converter converter;

    /** The column name. */
    private String columnName;

    /** The column index. */
    private int columnIndex = -1;

    /** The required. */
    private boolean required;

    /** The dynamic column property. */
    private boolean dynamicColumnProperty;

    /**
     * Gets the number class.
     *
     * @return the number class
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Number> getNumberClass() {
        if (Number.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
            return (Class<? extends Number>) propertyDescriptor.getPropertyType();
        }
        return null;
    }

    /**
     * Gets the property descriptor.
     *
     * @return the property descriptor
     */
    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    /**
     * Sets the property descriptor.
     *
     * @param propertyDescriptor
     *            the new property descriptor
     */
    public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }

    /**
     * Gets the converter.
     *
     * @return the converter
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * Sets the converter.
     *
     * @param converter
     *            the new converter
     */
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /**
     * Gets the column name.
     *
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the column name.
     *
     * @param columnName
     *            the new column name
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Checks if is required.
     *
     * @return true, if is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the required.
     *
     * @param required
     *            the new required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Gets the column index.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Sets the column index.
     *
     * @param columnIndex
     *            the new column index
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * Gets the field.
     *
     * @return the field
     */
    public Field getField() {
        return field;
    }

    /**
     * Sets the field.
     *
     * @param field
     *            the new field
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * Gets the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return propertyDescriptor.getName();
    }

    /**
     * Sets the dynamic column property.
     *
     * @param dynamicColumnProperty
     *            the new dynamic column property
     */
    public void setDynamicColumnProperty(boolean dynamicColumnProperty) {
        this.dynamicColumnProperty = dynamicColumnProperty;
    }

    /**
     * Checks if is dynamic column property.
     *
     * @return true, if is dynamic column property
     */
    public boolean isDynamicColumnProperty() {
        return this.dynamicColumnProperty;
    }

    @Override
    public int hashCode() {
        return getPropertyName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BeanProperty)) {
            return false;
        }
        BeanProperty other = (BeanProperty) obj;
        return getPropertyName().equals(other.getPropertyName());
    }

    /**
     * Gets the property type.
     *
     * @return the property type
     */
    public String getPropertyType() {
        return getConverter() != null ? getConverter().infoOnType()
                : getPropertyDescriptor().getPropertyType().getName();
    }

}
