package org.csveed.bean;

import org.csveed.bean.conversion.Converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class BeanProperty {

    private PropertyDescriptor propertyDescriptor;

    private Field field;

    private Converter converter = null;

    private String columnName = null;

    private int columnIndex = -1;

    private boolean required = false;

    @SuppressWarnings("unchecked")
    public Class<? extends Number> getNumberClass() {
        if (Number.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
            return (Class<? extends Number>)propertyDescriptor.getPropertyType();
        }
        return null;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getPropertyName() {
        return propertyDescriptor.getName();
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
        BeanProperty other = (BeanProperty)obj;
        return getPropertyName().equals(other.getPropertyName());
    }

    public String getPropertyType() {
        return getConverter() != null ?
                getConverter().infoOnType() :
                getPropertyDescriptor().getPropertyType().getName();
    }

}
