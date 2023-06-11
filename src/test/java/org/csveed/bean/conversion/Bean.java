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
import java.util.Currency;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * The Class Bean.
 */
public class Bean {

    /** The charset. */
    private Charset charset;

    /** The currency. */
    private Currency currency;

    /** The pattern. */
    private Pattern pattern;

    /** The time zone. */
    private TimeZone timeZone;

    /** The bytes. */
    private byte[] bytes;

    /** The chars. */
    private char[] chars;

    /** The char primitive. */
    private char charPrimitive;

    /** The character. */
    private Character character;

    /** The boolean primitive. */
    private boolean booleanPrimitive;

    /** The boolean object. */
    private Boolean booleanObject;

    /** The byte primitive. */
    private byte bytePrimitive;

    /** The byte object. */
    private Byte byteObject;

    /** The short primitive. */
    private short shortPrimitive;

    /** The short object. */
    private Short shortObject;

    /** The int primitive. */
    private int intPrimitive;

    /** The int object. */
    private Integer intObject;

    /** The long primitive. */
    private long longPrimitive;

    /** The long object. */
    private Long longObject;

    /** The float primitive. */
    private float floatPrimitive;

    /** The float object. */
    private Float floatObject;

    /** The double primitive. */
    private double doublePrimitive;

    /** The double object. */
    private Double doubleObject;

    /** The big decimal. */
    private BigDecimal bigDecimal;

    /** The big integer. */
    private BigInteger bigInteger;

    /**
     * Gets the charset.
     *
     * @return the charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Sets the charset.
     *
     * @param charset
     *            the new charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the currency.
     *
     * @param currency
     *            the new currency
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Gets the pattern.
     *
     * @return the pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern.
     *
     * @param pattern
     *            the new pattern
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets the time zone.
     *
     * @return the time zone
     */
    public TimeZone getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param timeZone
     *            the new time zone
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * Gets the bytes.
     *
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Sets the bytes.
     *
     * @param bytes
     *            the new bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Gets the chars.
     *
     * @return the chars
     */
    public char[] getChars() {
        return chars;
    }

    /**
     * Sets the chars.
     *
     * @param chars
     *            the new chars
     */
    public void setChars(char[] chars) {
        this.chars = chars;
    }

    /**
     * Gets the char primitive.
     *
     * @return the char primitive
     */
    public char getCharPrimitive() {
        return charPrimitive;
    }

    /**
     * Sets the char primitive.
     *
     * @param charPrimitive
     *            the new char primitive
     */
    public void setCharPrimitive(char charPrimitive) {
        this.charPrimitive = charPrimitive;
    }

    /**
     * Gets the character.
     *
     * @return the character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Sets the character.
     *
     * @param character
     *            the new character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Checks if is boolean primitive.
     *
     * @return true, if is boolean primitive
     */
    public boolean isBooleanPrimitive() {
        return booleanPrimitive;
    }

    /**
     * Sets the boolean primitive.
     *
     * @param booleanPrimitive
     *            the new boolean primitive
     */
    public void setBooleanPrimitive(boolean booleanPrimitive) {
        this.booleanPrimitive = booleanPrimitive;
    }

    /**
     * Gets the boolean object.
     *
     * @return the boolean object
     */
    public Boolean getBooleanObject() {
        return booleanObject;
    }

    /**
     * Sets the boolean object.
     *
     * @param booleanObject
     *            the new boolean object
     */
    public void setBooleanObject(Boolean booleanObject) {
        this.booleanObject = booleanObject;
    }

    /**
     * Gets the byte primitive.
     *
     * @return the byte primitive
     */
    public byte getBytePrimitive() {
        return bytePrimitive;
    }

    /**
     * Sets the byte primitive.
     *
     * @param bytePrimitive
     *            the new byte primitive
     */
    public void setBytePrimitive(byte bytePrimitive) {
        this.bytePrimitive = bytePrimitive;
    }

    /**
     * Gets the byte object.
     *
     * @return the byte object
     */
    public Byte getByteObject() {
        return byteObject;
    }

    /**
     * Sets the byte object.
     *
     * @param byteObject
     *            the new byte object
     */
    public void setByteObject(Byte byteObject) {
        this.byteObject = byteObject;
    }

    /**
     * Gets the short primitive.
     *
     * @return the short primitive
     */
    public short getShortPrimitive() {
        return shortPrimitive;
    }

    /**
     * Sets the short primitive.
     *
     * @param shortPrimitive
     *            the new short primitive
     */
    public void setShortPrimitive(short shortPrimitive) {
        this.shortPrimitive = shortPrimitive;
    }

    /**
     * Gets the short object.
     *
     * @return the short object
     */
    public Short getShortObject() {
        return shortObject;
    }

    /**
     * Sets the short object.
     *
     * @param shortObject
     *            the new short object
     */
    public void setShortObject(Short shortObject) {
        this.shortObject = shortObject;
    }

    /**
     * Gets the int primitive.
     *
     * @return the int primitive
     */
    public int getIntPrimitive() {
        return intPrimitive;
    }

    /**
     * Sets the int primitive.
     *
     * @param intPrimitive
     *            the new int primitive
     */
    public void setIntPrimitive(int intPrimitive) {
        this.intPrimitive = intPrimitive;
    }

    /**
     * Gets the int object.
     *
     * @return the int object
     */
    public Integer getIntObject() {
        return intObject;
    }

    /**
     * Sets the int object.
     *
     * @param intObject
     *            the new int object
     */
    public void setIntObject(Integer intObject) {
        this.intObject = intObject;
    }

    /**
     * Gets the long primitive.
     *
     * @return the long primitive
     */
    public long getLongPrimitive() {
        return longPrimitive;
    }

    /**
     * Sets the long primitive.
     *
     * @param longPrimitive
     *            the new long primitive
     */
    public void setLongPrimitive(long longPrimitive) {
        this.longPrimitive = longPrimitive;
    }

    /**
     * Gets the long object.
     *
     * @return the long object
     */
    public Long getLongObject() {
        return longObject;
    }

    /**
     * Sets the long object.
     *
     * @param longObject
     *            the new long object
     */
    public void setLongObject(Long longObject) {
        this.longObject = longObject;
    }

    /**
     * Gets the float primitive.
     *
     * @return the float primitive
     */
    public float getFloatPrimitive() {
        return floatPrimitive;
    }

    /**
     * Sets the float primitive.
     *
     * @param floatPrimitive
     *            the new float primitive
     */
    public void setFloatPrimitive(float floatPrimitive) {
        this.floatPrimitive = floatPrimitive;
    }

    /**
     * Gets the float object.
     *
     * @return the float object
     */
    public Float getFloatObject() {
        return floatObject;
    }

    /**
     * Sets the float object.
     *
     * @param floatObject
     *            the new float object
     */
    public void setFloatObject(Float floatObject) {
        this.floatObject = floatObject;
    }

    /**
     * Gets the double primitive.
     *
     * @return the double primitive
     */
    public double getDoublePrimitive() {
        return doublePrimitive;
    }

    /**
     * Sets the double primitive.
     *
     * @param doublePrimitive
     *            the new double primitive
     */
    public void setDoublePrimitive(double doublePrimitive) {
        this.doublePrimitive = doublePrimitive;
    }

    /**
     * Gets the double object.
     *
     * @return the double object
     */
    public Double getDoubleObject() {
        return doubleObject;
    }

    /**
     * Sets the double object.
     *
     * @param doubleObject
     *            the new double object
     */
    public void setDoubleObject(Double doubleObject) {
        this.doubleObject = doubleObject;
    }

    /**
     * Gets the big decimal.
     *
     * @return the big decimal
     */
    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    /**
     * Sets the big decimal.
     *
     * @param bigDecimal
     *            the new big decimal
     */
    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    /**
     * Gets the big integer.
     *
     * @return the big integer
     */
    public BigInteger getBigInteger() {
        return bigInteger;
    }

    /**
     * Sets the big integer.
     *
     * @param bigInteger
     *            the new big integer
     */
    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }
}
