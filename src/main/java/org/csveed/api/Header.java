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
package org.csveed.api;

import org.csveed.report.RowReport;

/**
 * The original header of the CSV file.
 */
public interface Header extends Iterable<String> {

    /**
     * Number of columns.
     *
     * @return the number of columns
     */
    int size();

    /**
     * Gets the name of the header column with passed index.
     *
     * @param indexColumn
     *            column index to find the name for
     *
     * @return name of the header column
     */
    String getName(int indexColumn);

    /**
     * Gets the index column of the first column with a certain name.
     *
     * @param columnName
     *            column name to find the index for
     *
     * @return index of the header column
     */
    int getIndex(String columnName);

    /**
     * Generate an error report on the header row.
     *
     * @return error report on the header row
     */
    RowReport reportOnEndOfLine();

}
