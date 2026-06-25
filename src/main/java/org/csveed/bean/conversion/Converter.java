/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

/**
 * Stateless converter from String to Object.
 *
 * @param <K>
 *            the Object to convert the String to
 */
public interface Converter<K> {

    /**
     * From string.
     *
     * @param text
     *            the text
     *
     * @return the k
     *
     * @throws Exception
     *             the exception
     */
    K fromString(String text) throws Exception;

    /**
     * To string.
     *
     * @param value
     *            the value
     *
     * @return the string
     *
     * @throws Exception
     *             the exception
     */
    String toString(K value) throws Exception;

    /**
     * Info on type.
     *
     * @return the string
     */
    String infoOnType();

    /**
     * Gets the type.
     *
     * @return the type
     */
    Class<K> getType();

}
