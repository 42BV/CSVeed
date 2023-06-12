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
package org.csveed.row;

import org.csveed.common.Column;
import org.csveed.report.RowReport;

/**
 * The Interface Line.
 */
public interface Line extends Iterable<String> {

    /**
     * Size.
     *
     * @return the int
     */
    int size();

    /**
     * Gets the.
     *
     * @param index
     *            the index
     *
     * @return the string
     */
    String get(int index);

    /**
     * Report on end of line.
     *
     * @return the row report
     */
    RowReport reportOnEndOfLine();

    /**
     * Report on column.
     *
     * @param column
     *            the column
     *
     * @return the row report
     */
    RowReport reportOnColumn(Column column);

}
