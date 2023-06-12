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

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.csveed.bean.AbstractMapper;
import org.csveed.bean.BeanInstructions;
import org.csveed.bean.BeanParser;
import org.csveed.bean.BeanReader;
import org.csveed.bean.BeanReaderImpl;
import org.csveed.bean.BeanWriter;
import org.csveed.bean.BeanWriterImpl;
import org.csveed.bean.conversion.Converter;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.row.RowInstructions;
import org.csveed.row.RowReader;
import org.csveed.row.RowReaderImpl;
import org.csveed.row.RowWriter;
import org.csveed.row.RowWriterImpl;

/**
 * The Class CsvClientImpl.
 *
 * @param <T>
 *            the generic type
 */
public class CsvClientImpl<T> implements CsvClient<T> {

    /** The bean reader. */
    private BeanReader<T> beanReader;

    /** The bean writer. */
    private BeanWriter<T> beanWriter;

    /** The row reader. */
    private RowReader rowReader;

    /** The row writer. */
    private RowWriter rowWriter;

    /** The row instructions. */
    private final RowInstructions rowInstructions;

    /** The bean instructions. */
    private BeanInstructions beanInstructions;

    /**
     * Instantiates a new csv client impl.
     *
     * @param writer
     *            the writer
     */
    public CsvClientImpl(Writer writer) {
        this.rowWriter = new RowWriterImpl(writer);
        this.rowInstructions = getRowWriter().getRowInstructions();
    }

    /**
     * Instantiates a new csv client impl.
     *
     * @param writer
     *            the writer
     * @param beanClass
     *            the bean class
     */
    public CsvClientImpl(Writer writer, Class<T> beanClass) {
        this(writer, new BeanParser().getBeanInstructions(beanClass));
    }

    /**
     * Instantiates a new csv client impl.
     *
     * @param writer
     *            the writer
     * @param beanInstructions
     *            the bean instructions
     */
    public CsvClientImpl(Writer writer, BeanInstructions beanInstructions) {
        this.beanWriter = new BeanWriterImpl<>(writer, beanInstructions);
        this.rowWriter = getBeanWriter().getRowWriter();
        this.rowInstructions = getRowWriter().getRowInstructions();
        this.beanInstructions = beanInstructions;
    }

    /**
     * Instantiates a new csv client impl.
     *
     * @param reader
     *            the reader
     */
    public CsvClientImpl(Reader reader) {
        this.rowReader = new RowReaderImpl(reader);
        this.rowInstructions = getRowReader().getRowInstructions();
    }

    /**
     * Instantiates a new csv client impl.
     *
     * @param reader
     *            the reader
     * @param beanClass
     *            the bean class
     */
    public CsvClientImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    /**
     * Instantiates a new csv client impl.
     *
     * @param reader
     *            the reader
     * @param beanInstructions
     *            the bean instructions
     */
    public CsvClientImpl(Reader reader, BeanInstructions beanInstructions) {
        this.beanReader = new BeanReaderImpl<>(reader, beanInstructions);
        this.rowReader = getBeanReader().getRowReader();
        this.rowInstructions = getRowReader().getRowInstructions();
        this.beanInstructions = beanInstructions;
    }

    @Override
    public void writeBeans(Collection<T> beans) {
        getBeanWriter().writeBeans(beans);
    }

    @Override
    public void writeBean(T bean) {
        getBeanWriter().writeBean(bean);
    }

    @Override
    public void writeRow(Row row) {
        getRowWriter().writeRow(row);
    }

    @Override
    public Row writeRow(String[] row) {
        return getRowWriter().writeRow(row);
    }

    @Override
    public void writeRows(Collection<Row> rows) {
        getRowWriter().writeRows(rows);
    }

    @Override
    public void writeRows(String[][] rows) {
        getRowWriter().writeRows(rows);
    }

    @Override
    public Header writeHeader(String[] header) {
        return getRowWriter().writeHeader(header);
    }

    @Override
    public void writeHeader(Header header) {
        getRowWriter().writeHeader(header);
    }

    @Override
    public void writeHeader() {
        getBeanWriter().writeHeader();
    }

    @Override
    public List<T> readBeans() {
        return getBeanReader().readBeans();
    }

    @Override
    public T readBean() {
        return getBeanReader().readBean();
    }

    @Override
    public List<Row> readRows() {
        return getRowReader().readRows();
    }

    @Override
    public Row readRow() {
        return getRowReader().readRow();
    }

    @Override
    public Header readHeader() {
        return getRowReader().readHeader();
    }

    @Override
    public int getCurrentLine() {
        return getRowReader().getCurrentLine();
    }

    @Override
    public boolean isFinished() {
        return getRowReader().isFinished();
    }

    @Override
    public CsvClient<T> setUseHeader(boolean useHeader) {
        rowInstructions.setUseHeader(useHeader);
        return this;
    }

    @Override
    public CsvClient<T> setStartRow(int startRow) {
        rowInstructions.setStartRow(startRow);
        return this;
    }

    @Override
    public CsvClient<T> setEscape(char symbol) {
        rowInstructions.setEscape(symbol);
        return this;
    }

    @Override
    public CsvClient<T> setQuote(char symbol) {
        rowInstructions.setQuote(symbol);
        return this;
    }

    @Override
    public CsvClient<T> setSeparator(char symbol) {
        rowInstructions.setSeparator(symbol);
        return this;
    }

    @Override
    public CsvClient<T> setComment(char symbol) {
        rowInstructions.setComment(symbol);
        return this;
    }

    @Override
    public CsvClient<T> setEndOfLine(char[] symbols) {
        rowInstructions.setEndOfLine(symbols);
        return this;
    }

    @Override
    public CsvClient<T> skipEmptyLines(boolean skip) {
        rowInstructions.skipEmptyLines(skip);
        return this;
    }

    @Override
    public CsvClient<T> skipCommentLines(boolean skip) {
        rowInstructions.skipCommentLines(skip);
        return this;
    }

    @Override
    public CsvClient<T> setMapper(Class<? extends AbstractMapper> mapper) {
        getBeanInstructions().setMapper(mapper);
        return this;
    }

    @Override
    public CsvClient<T> setDate(String propertyName, String dateFormat) {
        getBeanInstructions().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public CsvClient<T> setLocalizedNumber(String propertyName, Locale locale) {
        getBeanInstructions().setLocalizedNumber(propertyName, locale);
        return this;
    }

    @Override
    public CsvClient<T> setRequired(String propertyName, boolean required) {
        getBeanInstructions().setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvClient<T> setConverter(String propertyName, Converter converter) {
        getBeanInstructions().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public CsvClient<T> ignoreProperty(String propertyName) {
        getBeanInstructions().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public CsvClient<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        getBeanInstructions().mapColumnIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public CsvClient<T> mapColumnNameToProperty(String columnName, String propertyName) {
        getBeanInstructions().mapColumnNameToProperty(columnName, propertyName);
        return this;
    }

    @Override
    public CsvClient<T> setStartIndexDynamicColumns(int startIndex) {
        getBeanInstructions().setStartIndexDynamicColumns(startIndex);
        return this;
    }

    @Override
    public CsvClient<T> setHeaderNameToProperty(String propertyName) {
        getBeanInstructions().setHeaderNameToProperty(propertyName);
        return this;
    }

    @Override
    public CsvClient<T> setHeaderValueToProperty(String propertyName) {
        getBeanInstructions().setHeaderValueToProperty(propertyName);
        return this;
    }

    /**
     * Gets the bean instructions.
     *
     * @return the bean instructions
     */
    private BeanInstructions getBeanInstructions() {
        if (this.beanInstructions == null) {
            throw new CsvException(new GeneralError(
                    "BeanInstructions have not been initialized. Make sure to pass BeanInstructions or the bean class"
                            + " to CsvClient."));
        }
        return this.beanInstructions;
    }

    /**
     * Gets the bean reader.
     *
     * @return the bean reader
     */
    private BeanReader<T> getBeanReader() {
        if (this.beanReader == null) {
            throw new CsvException(new GeneralError(
                    "BeanReader has not been initialized. Make sure to pass BeanInstructions or the bean class to CsvClient."));
        }
        return this.beanReader;
    }

    /**
     * Gets the bean writer.
     *
     * @return the bean writer
     */
    private BeanWriter<T> getBeanWriter() {
        if (this.beanWriter == null) {
            throw new CsvException(new GeneralError(
                    "BeanWriter has not been initialized. Make sure to pass BeanInstructions or the bean class to CsvClient."));
        }
        return this.beanWriter;
    }

    /**
     * Gets the row reader.
     *
     * @return the row reader
     */
    private RowReader getRowReader() {
        if (this.rowReader == null) {
            throw new CsvException(new GeneralError(
                    "RowReader has not been initialized. Make sure to pass a Reader to the constructor."));
        }
        return this.rowReader;
    }

    /**
     * Gets the row writer.
     *
     * @return the row writer
     */
    public RowWriter getRowWriter() {
        if (this.rowWriter == null) {
            throw new CsvException(new GeneralError(
                    "RowWriter has not been initialized. Make sure to pass a Writer to the constructor."));
        }
        return rowWriter;
    }

}
