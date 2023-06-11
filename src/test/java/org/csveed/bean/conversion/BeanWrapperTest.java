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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Currency;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.csveed.bean.BeanInstructions;
import org.csveed.bean.BeanParser;
import org.csveed.bean.BeanProperties;
import org.csveed.common.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanWrapperTest.
 */
public class BeanWrapperTest {

    /** The properties. */
    private BeanProperties properties = deriveProperties();

    /** The bean. */
    private Bean bean;

    /** The default converters. */
    private DefaultConverters defaultConverters = new DefaultConverters();

    /** The bean wrapper. */
    private BeanWrapper beanWrapper;

    /**
     * Inits the.
     */
    @BeforeEach
    public void init() {
        bean = new Bean();
        beanWrapper = new BeanWrapper(defaultConverters, bean);
    }

    /**
     * Hit all properties.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void hitAllProperties() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charset")), null);
        beanWrapper.setProperty(properties.fromName(new Column("charset")), "");
        beanWrapper.setProperty(properties.fromName(new Column("chars")), null);
        beanWrapper.setProperty(properties.fromName(new Column("bytes")), null);
        beanWrapper.setProperty(properties.fromName(new Column("booleanObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("byteObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("shortObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("intObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("longObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("floatObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("doubleObject")), "");
        beanWrapper.setProperty(properties.fromName(new Column("bigDecimal")), "");
        beanWrapper.setProperty(properties.fromName(new Column("bigInteger")), "");
    }

    /**
     * Gets the charset.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getCharset() throws Exception {
        bean.setCharset(Charset.forName("US-ASCII"));
        assertEquals("US-ASCII", beanWrapper.getProperty(properties.fromName(new Column("charset"))));
    }

    /**
     * Sets the charset.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setCharset() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charset")), "US-ASCII");
        assertEquals("US-ASCII", bean.getCharset().displayName());
    }

    /**
     * Gets the currency.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getCurrency() throws Exception {
        bean.setCurrency(Currency.getInstance("USD"));
        assertEquals("USD", beanWrapper.getProperty(properties.fromName(new Column("currency"))));
    }

    /**
     * Sets the currency.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setCurrency() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("currency")), "USD");
        assertEquals("USD", bean.getCurrency().getCurrencyCode());
    }

    /**
     * Gets the pattern.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getPattern() throws Exception {
        bean.setPattern(Pattern.compile("[0-9]"));
        assertEquals("[0-9]", beanWrapper.getProperty(properties.fromName(new Column("pattern"))));
    }

    /**
     * Sets the pattern.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setPattern() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("pattern")), "[0-9]");
        assertEquals("[0-9]", bean.getPattern().pattern());
    }

    /**
     * Gets the time zone.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getTimeZone() throws Exception {
        bean.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        assertEquals("GMT-08:00", beanWrapper.getProperty(properties.fromName(new Column("timeZone"))));
    }

    /**
     * Sets the time zone.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setTimeZone() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("timeZone")), "GMT-8");
        assertEquals("GMT-08:00", bean.getTimeZone().getDisplayName());
    }

    /**
     * Gets the bytes.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBytes() throws Exception {
        bean.setBytes(new byte[] { 65, 66, 67 });
        assertEquals("ABC", beanWrapper.getProperty(properties.fromName(new Column("bytes"))));
    }

    /**
     * Sets the bytes.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBytes() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bytes")), "ABC");
        assertTrue(Arrays.equals(new byte[] { 65, 66, 67 }, bean.getBytes()));
    }

    /**
     * Gets the chars.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getChars() throws Exception {
        bean.setChars(new char[] { 'A', 'B', 'C' });
        assertEquals("ABC", beanWrapper.getProperty(properties.fromName(new Column("chars"))));
    }

    /**
     * Sets the chars.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setChars() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("chars")), "ABC");
        assertTrue(Arrays.equals(new char[] { 'A', 'B', 'C' }, bean.getChars()));
    }

    /**
     * Gets the char primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getCharPrimitive() throws Exception {
        bean.setCharPrimitive('ü');
        assertEquals("ü", beanWrapper.getProperty(properties.fromName(new Column("charPrimitive"))));
    }

    /**
     * Sets the char primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setCharPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charPrimitive")), "ü");
        assertEquals('ü', bean.getCharPrimitive());
    }

    /**
     * Gets the character.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getCharacter() throws Exception {
        bean.setCharacter('ü');
        assertEquals("ü", beanWrapper.getProperty(properties.fromName(new Column("character"))));
    }

    /**
     * Sets the character.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setCharacter() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("character")), "ü");
        assertEquals('ü', (char) bean.getCharacter());
    }

    /**
     * Gets the boolean primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBooleanPrimitive() throws Exception {
        bean.setBooleanPrimitive(true);
        assertEquals("true", beanWrapper.getProperty(properties.fromName(new Column("booleanPrimitive"))));
    }

    /**
     * Sets the boolean primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBooleanPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("booleanPrimitive")), "on");
        assertEquals(true, bean.isBooleanPrimitive());
    }

    /**
     * Gets the boolean object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBooleanObject() throws Exception {
        bean.setBooleanObject(Boolean.TRUE);
        assertEquals("true", beanWrapper.getProperty(properties.fromName(new Column("booleanObject"))));
    }

    /**
     * Sets the boolean object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBooleanObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("booleanObject")), "on");
        assertEquals(Boolean.TRUE, bean.getBooleanObject());
    }

    /**
     * Gets the byte primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBytePrimitive() throws Exception {
        bean.setBytePrimitive((byte) 17);
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("bytePrimitive"))));
    }

    /**
     * Sets the byte primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBytePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bytePrimitive")), "17");
        assertEquals(17, bean.getBytePrimitive());
    }

    /**
     * Gets the byte object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getByteObject() throws Exception {
        bean.setByteObject(Byte.valueOf("17"));
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("byteObject"))));
    }

    /**
     * Sets the byte object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setByteObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("byteObject")), "17");
        assertEquals(Byte.valueOf("17"), bean.getByteObject());
    }

    /**
     * Gets the short primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getShortPrimitive() throws Exception {
        bean.setShortPrimitive((short) 17);
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("shortPrimitive"))));
    }

    /**
     * Sets the short primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setShortPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("shortPrimitive")), "17");
        assertEquals(17, bean.getShortPrimitive());
    }

    /**
     * Gets the short object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getShortObject() throws Exception {
        bean.setShortObject(Short.valueOf("17"));
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("shortObject"))));
    }

    /**
     * Sets the short object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setShortObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("shortObject")), "17");
        assertEquals(Short.valueOf("17"), bean.getShortObject());
    }

    /**
     * Gets the int primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getIntPrimitive() throws Exception {
        bean.setIntPrimitive(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("intPrimitive"))));
    }

    /**
     * Sets the int primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setIntPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("intPrimitive")), "1989");
        assertEquals(1989, bean.getIntPrimitive());
    }

    /**
     * Gets the int object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getIntObject() throws Exception {
        bean.setIntObject(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("intObject"))));
    }

    /**
     * Sets the int object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setIntObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("intObject")), "1989");
        assertEquals((Integer) 1989, bean.getIntObject());
    }

    /**
     * Gets the long primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getLongPrimitive() throws Exception {
        bean.setLongPrimitive(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("longPrimitive"))));
    }

    /**
     * Sets the long primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setLongPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("longPrimitive")), "1989");
        assertEquals(1989, bean.getLongPrimitive());
    }

    /**
     * Gets the long object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getLongObject() throws Exception {
        bean.setLongObject(989L);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("longObject"))));
    }

    /**
     * Sets the long object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setLongObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("longObject")), "1989");
        assertEquals((Long) 1989L, bean.getLongObject());
    }

    /**
     * Gets the float primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getFloatPrimitive() throws Exception {
        bean.setFloatPrimitive((float) 42.42);
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("floatPrimitive"))));
    }

    /**
     * Sets the float primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setFloatPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("floatPrimitive")), "42.42");
        assertEquals((float) 42.42, bean.getFloatPrimitive(), 1);
    }

    /**
     * Gets the float object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getFloatObject() throws Exception {
        bean.setFloatObject(Float.valueOf("42.42"));
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("floatObject"))));
    }

    /**
     * Sets the float object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setFloatObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("floatObject")), "42.42");
        assertEquals(Float.valueOf("42.42"), bean.getFloatObject());
    }

    /**
     * Gets the double primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getDoublePrimitive() throws Exception {
        bean.setDoublePrimitive(42.42);
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("doublePrimitive"))));
    }

    /**
     * Sets the double primitive.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setDoublePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("doublePrimitive")), "42.42");
        assertEquals(42.42, bean.getDoublePrimitive(), 1);
    }

    /**
     * Gets the double object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getDoubleObject() throws Exception {
        bean.setDoubleObject(Double.valueOf("42.42"));
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("doubleObject"))));
    }

    /**
     * Sets the double object.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setDoubleObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("doubleObject")), "42.42");
        assertEquals(Double.valueOf("42.42"), bean.getDoubleObject());
    }

    /**
     * Gets the big decimal.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBigDecimal() throws Exception {
        bean.setBigDecimal(new BigDecimal("42.123"));
        assertEquals("42.123", beanWrapper.getProperty(properties.fromName(new Column("bigDecimal"))));
    }

    /**
     * Sets the big decimal.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBigDecimal() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bigDecimal")), "42.12345678901234567890");
        assertEquals(new BigDecimal("42.12345678901234567890"), bean.getBigDecimal());
    }

    /**
     * Gets the big integer.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void getBigInteger() throws Exception {
        bean.setBigInteger(new BigInteger("4212345678901234567890"));
        assertEquals("4212345678901234567890", beanWrapper.getProperty(properties.fromName(new Column("bigInteger"))));
    }

    /**
     * Sets the big integer.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void setBigInteger() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bigInteger")), "4212345678901234567890");
        assertEquals(new BigInteger("4212345678901234567890"), bean.getBigInteger());
    }

    /**
     * Derive properties.
     *
     * @return the bean properties
     */
    protected BeanProperties deriveProperties() {
        BeanInstructions instructions = new BeanParser().getBeanInstructions(Bean.class);
        return instructions.getProperties();
    }
}
