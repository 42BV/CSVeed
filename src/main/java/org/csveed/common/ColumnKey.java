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
