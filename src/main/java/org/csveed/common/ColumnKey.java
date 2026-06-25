/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.common;

/**
 * The Class ColumnKey.
 */
public abstract class ColumnKey implements Comparable<ColumnKey> {

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    public abstract Integer getPriority();

    /**
     * Same key type.
     *
     * @param columnKey
     *            the column key
     *
     * @return true, if successful
     */
    public boolean sameKeyType(ColumnKey columnKey) {
        return getPriority().equals(columnKey.getPriority());
    }

    /**
     * Key type compare.
     *
     * @param columnKey
     *            the column key
     *
     * @return the int
     */
    public int keyTypeCompare(ColumnKey columnKey) {
        return getPriority().compareTo(columnKey.getPriority());
    }

}
