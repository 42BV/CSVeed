/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean;

import java.util.Collection;

import org.csveed.row.RowWriter;

/**
 * Class for writing Beans.
 *
 * @param <T>
 *            the generic type
 */
public interface BeanWriter<T> {

    /**
     * Writes a collection of Beans to the table.
     *
     * @param beans
     *            beans to write to the table
     */
    void writeBeans(Collection<T> beans);

    /**
     * Writes a single Bean to the table.
     *
     * @param bean
     *            bean to write to the table
     */
    void writeBean(T bean);

    /**
     * Writes the header of a Bean type to the table.
     */
    void writeHeader();

    /**
     * Gets the row writer.
     *
     * @return the row writer
     */
    RowWriter getRowWriter();

}
