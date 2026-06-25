/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
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
