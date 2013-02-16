package nl.tweeenveertig.csveed.bean.instructions;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;

public class BeanProperty {

    private PropertyDescriptor propertyDescriptor;

    private PropertyEditor converter = null;

    private String name;

    private boolean required = false;

    private int indexColumn;

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }

    public PropertyEditor getConverter() {
        return converter;
    }

    public void setConverter(PropertyEditor converter) {
        this.converter = converter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
}
