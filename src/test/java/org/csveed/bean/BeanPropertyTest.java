package org.csveed.bean;

import org.csveed.bean.conversion.Converter;
import org.csveed.test.converters.BeanSimpleConverter;
import org.csveed.test.model.BeanSimple;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BeanPropertyTest {

    @Test
    public void construct() throws IntrospectionException {
        BeanProperty property = new BeanProperty();
        Converter converter = new BeanSimpleConverter();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("name", BeanSimple.class);
        property.setConverter(converter);
        property.setColumnIndex(3);
        property.setColumnName("name");
        property.setPropertyDescriptor(propertyDescriptor);
        property.setRequired(true);
        assertEquals(converter, property.getConverter());
        assertEquals(3, property.getColumnIndex());
        assertEquals("name", property.getColumnName());
        assertEquals(propertyDescriptor, property.getPropertyDescriptor());
        assertTrue(property.isRequired());
    }

}
