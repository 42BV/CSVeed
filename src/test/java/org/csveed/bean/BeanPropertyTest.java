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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import org.csveed.bean.conversion.Converter;
import org.csveed.test.converters.BeanSimpleConverter;
import org.csveed.test.model.BeanSimple;
import org.csveed.test.model.BeanVariousNotAnnotated;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanPropertyTest.
 */
public class BeanPropertyTest {

    /**
     * Construct.
     *
     * @throws IntrospectionException
     *             the introspection exception
     */
    @Test
    public void construct() throws IntrospectionException {
        BeanProperty property = new BeanProperty();
        Converter<BeanSimple> converter = new BeanSimpleConverter();
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

    /**
     * Number class.
     *
     * @throws IntrospectionException
     *             the introspection exception
     */
    @Test
    public void numberClass() throws IntrospectionException {
        BeanProperty property = new BeanProperty();
        property.setPropertyDescriptor(new PropertyDescriptor("number", BeanVariousNotAnnotated.class));
        assertNotNull(property.getNumberClass());
    }

    /**
     * Not A number class.
     *
     * @throws IntrospectionException
     *             the introspection exception
     */
    @Test
    public void notANumberClass() throws IntrospectionException {
        BeanProperty property = new BeanProperty();
        property.setPropertyDescriptor(new PropertyDescriptor("name", BeanSimple.class));
        assertNull(property.getNumberClass());
    }

}
