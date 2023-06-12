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

import java.io.Writer;
import java.util.Collection;

import org.csveed.bean.conversion.BeanWrapper;
import org.csveed.bean.conversion.ConversionException;
import org.csveed.bean.conversion.DefaultConverters;
import org.csveed.row.HeaderImpl;
import org.csveed.row.LineWithInfo;
import org.csveed.row.RowImpl;
import org.csveed.row.RowWriter;
import org.csveed.row.RowWriterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class BeanWriterImpl.
 *
 * @param <T>
 *            the generic type
 */
public class BeanWriterImpl<T> implements BeanWriter<T> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(BeanWriterImpl.class);

    /** The row writer. */
    private final RowWriter rowWriter;

    /** The bean instructions. */
    private final BeanInstructions beanInstructions;

    /** The header written. */
    private boolean headerWritten;

    /** The default converters. */
    private DefaultConverters defaultConverters = new DefaultConverters();

    /** The header. */
    private HeaderImpl header;

    /**
     * Instantiates a new bean writer impl.
     *
     * @param writer
     *            the writer
     * @param beanClass
     *            the bean class
     */
    public BeanWriterImpl(Writer writer, Class<T> beanClass) {
        this(writer, new BeanParser().getBeanInstructions(beanClass));
    }

    /**
     * Instantiates a new bean writer impl.
     *
     * @param writer
     *            the writer
     * @param beanInstructions
     *            the bean instructions
     */
    public BeanWriterImpl(Writer writer, BeanInstructions beanInstructions) {
        this.beanInstructions = beanInstructions;
        this.rowWriter = new RowWriterImpl(writer, this.beanInstructions.getRowInstructions());
    }

    @Override
    public void writeBeans(Collection<T> beans) {
        for (T bean : beans) {
            writeBean(bean);
        }
    }

    @Override
    public void writeBean(T bean) {
        writeHeader();

        LineWithInfo line = new LineWithInfo();

        BeanWrapper beanWrapper = new BeanWrapper(defaultConverters, bean);
        for (BeanProperty property : beanInstructions.getProperties()) {
            try {
                line.addCell(beanWrapper.getProperty(property));
            } catch (ConversionException e) {
                logger.error("{}", e.getMessage());
                logger.trace("", e);
            }
        }
        rowWriter.writeRow(new RowImpl(line, header));
    }

    @Override
    public void writeHeader() {
        if (!beanInstructions.useHeader() || headerWritten) {
            return;
        }
        LineWithInfo line = new LineWithInfo();
        for (BeanProperty property : beanInstructions.getProperties()) {
            line.addCell(property.getColumnName());
        }
        header = new HeaderImpl(line);
        rowWriter.writeHeader(header);
        headerWritten = true;
    }

    @Override
    public RowWriter getRowWriter() {
        return rowWriter;
    }

}
