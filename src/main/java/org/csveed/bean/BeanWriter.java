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

import java.util.Collection;

import org.csveed.row.RowWriter;

/**
 * Class for writing Beans
 */
public interface BeanWriter<T> {

    /**
     * Writes a collection of Beans to the table
     *
     * @param beans
     *            beans to write to the table
     */
    void writeBeans(Collection<T> beans);

    /**
     * Writes a single Bean to the table
     *
     * @param bean
     *            bean to write to the table
     */
    void writeBean(T bean);

    /**
     * Writes the header of a Bean type to the table
     */
    void writeHeader();

    RowWriter getRowWriter();

}
