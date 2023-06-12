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
package org.csveed.bean;

import java.util.List;

import org.csveed.api.Header;
import org.csveed.row.RowReader;

/**
 * <p>
 * The BeanReader is responsible for reading CSV rows and converting those into beans. There are two ways to initialize
 * a {@link org.csveed.bean.BeanReaderImpl}:
 * </p>
 * <ul>
 * <li>Point it to class. The {@link org.csveed.annotations} in the class are read, as is the order of the properties
 * within the class.</li>
 * <li>Roll your own. Pass a {@link BeanInstructions} implementation with your own configuration settings</li>
 * </ul>
 *
 * @author Robert Bor
 *
 * @param <T>
 *            the bean class into which the rows are converted
 */
public interface BeanReader<T> {

    /**
     * Reads all rows from the file and return these as beans.
     *
     * @return all beans read from the Reader
     */
    List<T> readBeans();

    /**
     * Reads a single row and returns this as a bean. The RowReader will keep track of its state.
     *
     * @return Bean read from the Reader
     */
    T readBean();

    /**
     * Returns the first readable line of the CSV file as header, regardless if useHeader==true.
     *
     * @return header
     */
    Header readHeader();

    /**
     * Returns the line from which the bean was read. Note that a line is seen as a legitimate CSV row, not necessarily
     * a printable line (unless multi-lines are used, these values are the same).
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
     * Returns the underlying line reader for the bean reader.
     *
     * @return the underlying line reader
     */
    RowReader getRowReader();

    /**
     * The set of instructions for dealing with beans.
     *
     * @return bean instructions
     */
    BeanInstructions getBeanInstructions();

}
