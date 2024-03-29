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

import static org.csveed.bean.conversion.ConversionUtil.trimAllWhitespace;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * The Class NumberUtils.
 */
public abstract class NumberUtils {

    /**
     * Instantiates a new number utils.
     */
    private NumberUtils() {
        // Do not allow instantiation of static utils class
    }

    /**
     * Convert number to target class.
     *
     * @param <T>
     *            the generic type
     * @param number
     *            the number
     * @param targetClass
     *            the target class
     *
     * @return the t
     *
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass)
            throws IllegalArgumentException {

        if (targetClass.isInstance(number)) {
            return (T) number;
        }
        if (targetClass.equals(Byte.class)) {
            long value = number.longValue();
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Byte.valueOf(number.byteValue());
        }
        if (targetClass.equals(Short.class)) {
            long value = number.longValue();
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Short.valueOf(number.shortValue());
        }
        if (targetClass.equals(Integer.class)) {
            long value = number.longValue();
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) Integer.valueOf(number.intValue());
        }
        if (targetClass.equals(Long.class)) {
            return (T) Long.valueOf(number.longValue());
        }
        if (targetClass.equals(BigInteger.class)) {
            if (number instanceof BigDecimal) {
                // do not lose precision - use BigDecimal's own conversion
                return (T) ((BigDecimal) number).toBigInteger();
            }
            // original value is not a Big* number - use standard long conversion
            return (T) BigInteger.valueOf(number.longValue());
        }
        if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(number.floatValue());
        }
        if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(number.doubleValue());
        }
        if (targetClass.equals(BigDecimal.class)) {
            // always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
            // (see BigDecimal javadoc for details)
            return (T) new BigDecimal(number.toString());
        }
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
                + number.getClass().getName() + "] to unknown target class [" + targetClass.getName() + "]");
    }

    /**
     * Raise overflow exception.
     *
     * @param number
     *            the number
     * @param targetClass
     *            the target class
     */
    private static void raiseOverflowException(Number number, Class targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
                + number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    /**
     * Parses the number.
     *
     * @param <T>
     *            the generic type
     * @param text
     *            the text
     * @param targetClass
     *            the target class
     *
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        String trimmed = trimAllWhitespace(text);

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        }
        if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        }
        if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        }
        if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        }
        if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        }
        if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        }
        if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        }
        if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return (T) new BigDecimal(trimmed);
        }
        throw new IllegalArgumentException(
                "Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
    }

    /**
     * Parses the number.
     *
     * @param <T>
     *            the generic type
     * @param text
     *            the text
     * @param targetClass
     *            the target class
     * @param numberFormat
     *            the number format
     *
     * @return the t
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass, NumberFormat numberFormat) {
        if (numberFormat != null) {
            DecimalFormat decimalFormat = null;
            boolean resetBigDecimal = false;
            if (numberFormat instanceof DecimalFormat) {
                decimalFormat = (DecimalFormat) numberFormat;
                if (BigDecimal.class.equals(targetClass) && !decimalFormat.isParseBigDecimal()) {
                    decimalFormat.setParseBigDecimal(true);
                    resetBigDecimal = true;
                }
            }
            try {
                Number number = numberFormat.parse(trimAllWhitespace(text));
                return convertNumberToTargetClass(number, targetClass);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse number: " + ex.getMessage());
            } finally {
                if (resetBigDecimal && decimalFormat != null) {
                    decimalFormat.setParseBigDecimal(false);
                }
            }
        }
        return parseNumber(text, targetClass);
    }

    /**
     * Checks if is hex number.
     *
     * @param value
     *            the value
     *
     * @return true, if is hex number
     */
    private static boolean isHexNumber(String value) {
        int index = value.startsWith("-") ? 1 : 0;
        return value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index);
    }

    /**
     * Decode big integer.
     *
     * @param value
     *            the value
     *
     * @return the big integer
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        // Handle minus sign, if present.
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // Handle radix specifier, if present.
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return negative ? result.negate() : result;
    }

}
