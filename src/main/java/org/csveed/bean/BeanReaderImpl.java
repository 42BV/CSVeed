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

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.row.RowReader;
import org.csveed.row.RowReaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanReaderImpl<T> implements BeanReader<T> {

    private static final Logger logger = LoggerFactory.getLogger(BeanReaderImpl.class);

    private final RowReader rowReader;

    private final BeanInstructions beanInstructions;

    private AbstractMapper<T> mapper;

    private final DynamicColumn currentDynamicColumn;

    private Row unmappedRow;

    public BeanReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public BeanReaderImpl(Reader reader, BeanInstructions beanInstructions) {
        this.beanInstructions = beanInstructions;
        this.rowReader = new RowReaderImpl(reader, this.beanInstructions.getRowInstructions());
        this.currentDynamicColumn = new DynamicColumn(this.beanInstructions.getStartIndexDynamicColumns());
    }

    public AbstractMapper<T> getMapper() {
        if (this.mapper == null) {
            this.mapper = this.createMappingStrategy();
            mapper.setBeanInstructions(this.beanInstructions);
        }
        return mapper;
    }

    @Override
    public List<T> readBeans() {
        List<T> beans = new ArrayList<>();
        while (!isFinished()) {
            T bean = readBean();
            if (bean != null) {
                beans.add(bean);
            }
        }
        return beans;
    }

    @Override
    public T readBean() {
        logSettings();

        if (this.beanInstructions.useHeader()) {
            getMapper().verifyHeader(getHeader());
        }

        currentDynamicColumn.checkForReset(((RowReaderImpl) rowReader).getMaxNumberOfColumns());
        if (currentDynamicColumn.atFirstDynamicColumn()) {
            unmappedRow = rowReader.readRow();
        }
        if (unmappedRow == null) {
            return null;
        }

        T bean = getMapper().convert(instantiateBean(), unmappedRow, getCurrentLine(), currentDynamicColumn);
        currentDynamicColumn.advanceDynamicColumn();
        return bean;
    }

    protected void logSettings() {
        this.beanInstructions.logSettings();
    }

    protected Header getHeader() {
        if (this.beanInstructions.useHeader()) {
            return rowReader.getHeader();
        }
        return null;
    }

    @Override
    public Header readHeader() {
        return rowReader.readHeader();
    }

    @Override
    public int getCurrentLine() {
        return this.rowReader.getCurrentLine();
    }

    @Override
    public boolean isFinished() {
        return rowReader.isFinished();
    }

    @Override
    public RowReader getRowReader() {
        return this.rowReader;
    }

    private T instantiateBean() {
        try {
            return this.getBeanClass().getDeclaredConstructor().newInstance();
        } catch (Exception err) {
            throw new CsvException(new GeneralError("Unable to instantiate the bean class "
                    + this.getBeanClass().getName() + ". Does it have a no-arg public constructor?"));
        }
    }

    @SuppressWarnings("unchecked")
    public Class<T> getBeanClass() {
        return this.beanInstructions.getBeanClass();
    }

    @SuppressWarnings("unchecked")
    public AbstractMapper<T> createMappingStrategy() {
        try {
            return this.beanInstructions.getMappingStrategy().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.trace("", e);
            throw new CsvException(new GeneralError("Unable to instantiate the mapping strategy"));
        }
    }

    @Override
    public BeanInstructions getBeanInstructions() {
        return this.beanInstructions;
    }

}
