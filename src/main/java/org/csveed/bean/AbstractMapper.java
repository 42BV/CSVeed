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

import java.util.Set;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.bean.conversion.BeanWrapper;
import org.csveed.bean.conversion.ConversionException;
import org.csveed.bean.conversion.DefaultConverters;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;

/**
 * The Class AbstractMapper.
 *
 * @param <T>
 *            the generic type
 */
public abstract class AbstractMapper<T> {

    /** The bean instructions. */
    protected BeanInstructions beanInstructions;

    /** The verified. */
    private boolean verified;

    /** The default converters. */
    private DefaultConverters defaultConverters = new DefaultConverters();

    /**
     * Gets the bean property.
     *
     * @param currentColumn
     *            the current column
     *
     * @return the bean property
     */
    public abstract BeanProperty getBeanProperty(Column currentColumn);

    /**
     * Keys.
     *
     * @return the sets the
     */
    protected abstract Set<Column> keys();

    /**
     * Check key.
     *
     * @param header
     *            the header
     * @param key
     *            the key
     */
    protected abstract void checkKey(Header header, Column key);

    /**
     * Verify header.
     *
     * @param header
     *            the header
     */
    public void verifyHeader(Header header) {
        if (verified) {
            return;
        }
        for (Column key : keys()) {
            if (!getBeanProperty(key).isDynamicColumnProperty()) {
                checkKey(header, key);
            }
        }
        verified = true;
    }

    /**
     * Gets the column.
     *
     * @param row
     *            the row
     *
     * @return the column
     */
    protected abstract Column getColumn(Row row);

    /**
     * Convert.
     *
     * @param bean
     *            the bean
     * @param row
     *            the row
     * @param lineNumber
     *            the line number
     * @param currentDynamicColumn
     *            the current dynamic column
     *
     * @return the t
     */
    public T convert(T bean, Row row, int lineNumber, DynamicColumn currentDynamicColumn) {
        BeanWrapper beanWrapper = new BeanWrapper(defaultConverters, bean);

        Column currentColumn = null;
        for (String cell : row) {
            currentColumn = currentColumn == null ? getColumn(row) : currentColumn.nextColumn();

            if (currentDynamicColumn.isDynamicColumnActive(currentColumn)) {
                setDynamicColumnProperties(row, lineNumber, beanWrapper, currentColumn);
                continue;
            }

            BeanProperty beanProperty = getBeanProperty(currentColumn);
            if (beanProperty == null) {
                continue;
            }
            if (beanProperty.isRequired() && (cell == null || cell.equals(""))) {
                throw new CsvException(new RowError(
                        "Bean property \"" + beanProperty.getPropertyName()
                                + "\" is required and may not be empty or null",
                        row.reportOnColumn(currentColumn.getColumnIndex()), lineNumber));
            }
            setBeanProperty(row, lineNumber, beanWrapper, currentColumn, cell, beanProperty);
        }
        return bean;
    }

    /**
     * Sets the dynamic column properties.
     *
     * @param row
     *            the row
     * @param lineNumber
     *            the line number
     * @param beanWrapper
     *            the bean wrapper
     * @param currentColumn
     *            the current column
     */
    private void setDynamicColumnProperties(Row row, int lineNumber, BeanWrapper beanWrapper, Column currentColumn) {
        BeanProperty headerNameProperty = beanInstructions.getProperties().getHeaderNameProperty();
        if (headerNameProperty != null) {
            String dynamicHeaderName = row.getHeader().getName(currentColumn.getColumnIndex());
            setBeanProperty(row, lineNumber, beanWrapper, currentColumn, dynamicHeaderName, headerNameProperty);
        }

        BeanProperty headerValueProperty = beanInstructions.getProperties().getHeaderValueProperty();
        if (headerValueProperty != null) {
            String dynamicHeaderValue = row.get(currentColumn.getColumnIndex());
            setBeanProperty(row, lineNumber, beanWrapper, currentColumn, dynamicHeaderValue, headerValueProperty);
        }
    }

    /**
     * Sets the bean property.
     *
     * @param row
     *            the row
     * @param lineNumber
     *            the line number
     * @param beanWrapper
     *            the bean wrapper
     * @param currentColumn
     *            the current column
     * @param cell
     *            the cell
     * @param beanProperty
     *            the bean property
     */
    private void setBeanProperty(Row row, int lineNumber, BeanWrapper beanWrapper, Column currentColumn, String cell,
            BeanProperty beanProperty) {
        try {
            beanWrapper.setProperty(beanProperty, cell);
        } catch (ConversionException err) {
            String message = err.getMessage() + " cell" + currentColumn.getColumnText() + " [" + cell + "] to "
                    + beanProperty.getPropertyName() + ": " + err.getTypeDescription();
            throw new CsvException(
                    new RowError(message, row.reportOnColumn(currentColumn.getColumnIndex()), lineNumber));
        }
    }

    /**
     * Sets the bean instructions.
     *
     * @param beanInstructions
     *            the new bean instructions
     */
    public void setBeanInstructions(BeanInstructions beanInstructions) {
        this.beanInstructions = beanInstructions;
    }

}
