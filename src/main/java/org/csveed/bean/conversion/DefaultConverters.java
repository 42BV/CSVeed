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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * The Class DefaultConverters.
 */
public class DefaultConverters {

    /** The converters. */
    private Map<Class<?>, Converter> converters = new HashMap<>();

    /**
     * Instantiates a new default converters.
     */
    public DefaultConverters() {
        registerConverters();
    }

    /**
     * Gets the converter.
     *
     * @param clazz
     *            the clazz
     *
     * @return the converter
     */
    public Converter getConverter(Class clazz) {
        return converters.get(clazz);
    }

    /**
     * Register converters.
     */
    protected void registerConverters() {
        addConverter(Charset.class, new CharsetConverter());
        addConverter(Currency.class, new CurrencyConverter());
        addConverter(Pattern.class, new PatternConverter());
        addConverter(TimeZone.class, new TimeZoneConverter());
        addConverter(byte[].class, new ByteArrayConverter());
        addConverter(char[].class, new CharArrayConverter());
        addConverter(char.class, new CharacterConverter(false));
        addConverter(Character.class, new CharacterConverter(true));
        addConverter(boolean.class, new CustomBooleanConverter(false));
        addConverter(Boolean.class, new CustomBooleanConverter(true));
        addConverter(byte.class,
                new CustomNumberConverter(Byte.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Byte.class,
                new CustomNumberConverter(Byte.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(short.class,
                new CustomNumberConverter(Short.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Short.class,
                new CustomNumberConverter(Short.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(int.class,
                new CustomNumberConverter(Integer.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Integer.class,
                new CustomNumberConverter(Integer.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(long.class,
                new CustomNumberConverter(Long.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Long.class,
                new CustomNumberConverter(Long.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(float.class,
                new CustomNumberConverter(Float.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Float.class,
                new CustomNumberConverter(Float.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(double.class,
                new CustomNumberConverter(Double.class, NumberFormat.getNumberInstance(Locale.US), false));
        addConverter(Double.class,
                new CustomNumberConverter(Double.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(BigDecimal.class,
                new CustomNumberConverter(BigDecimal.class, NumberFormat.getNumberInstance(Locale.US), true));
        addConverter(BigInteger.class, new CustomNumberConverter(BigInteger.class, true));
        addConverter(String.class, new StringConverter());
    }

    /**
     * Adds the converter.
     *
     * @param clazz
     *            the clazz
     * @param converter
     *            the converter
     */
    protected void addConverter(Class clazz, Converter converter) {
        converters.put(clazz, converter);
    }

}
