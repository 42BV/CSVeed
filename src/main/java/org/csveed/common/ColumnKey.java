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

public abstract class ColumnKey implements Comparable<ColumnKey> {

    public abstract Integer getPriority();

    public boolean sameKeyType(ColumnKey columnKey) {
        return getPriority().equals(columnKey.getPriority());
    }

    public int keyTypeCompare(ColumnKey columnKey) {
        return getPriority().compareTo(columnKey.getPriority());
    }

}
