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

import java.util.List;

import org.csveed.api.Header;
import org.csveed.api.Row;

/**
 * LineReaders reads rows from the CSV file and returns those all at once, or one by one if desired.
 *
 * @author Robert Bor
 */
public interface RowReader {

    /**
     * Reads all rows from the file and returns them as a List. After this, the RowReader will be finished
     *
     * @return all Rows read from the Reader
     */
    List<Row> readRows();

    /**
     * Reads a single row from the file and returns this. The RowReader will keep track of its state.
     *
     * @return Row read from the Reader
     */
    Row readRow();

    /**
     * Returns the line from which the row was read. Note that a line is seen as a legitimate CSV row, not necessarily a
     * printable line (unless multi-lines are used, these values are the same).
     *
     * @return current line number
     */
    int getCurrentLine();

    /**
     * States whether the Reader is done with the file.
     *
     * @return true if file is finished
     */
    boolean isFinished();

    /**
     * Returns the first readable line of the CSV file as header, regardless if useHeader==true.
     *
     * @return header
     */
    Header readHeader();

    /**
     * Returns the header of the CSV file. Only possibly returns a value when useHeader==true
     *
     * @return header or null if the useHeader==false
     */
    Header getHeader();

    /**
     * The set of instructions for dealing with rows.
     *
     * @return row instructions
     */
    RowInstructions getRowInstructions();

}
