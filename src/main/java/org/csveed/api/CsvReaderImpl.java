package org.csveed.api;

import org.csveed.bean.*;
import org.csveed.bean.conversion.Converter;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.row.RowReader;
import org.csveed.row.RowReaderImpl;

import java.io.Reader;
import java.util.List;
import java.util.Locale;

public class CsvReaderImpl<T> implements CsvReader<T> {

    private BeanReader<T> beanReader;

    private RowReader rowReader;

    public CsvReaderImpl(Reader reader) {
        this.rowReader = new RowReaderImpl(reader);
    }
    
    public CsvReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public CsvReaderImpl(Reader reader, BeanInstructions beanInstructions) {
        this.beanReader = new BeanReaderImpl<T>(reader, beanInstructions);
        this.rowReader = getBeanReader().getRowReader();
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
    public CsvReader<T> setUseHeader(boolean useHeader) {
        getRowReader().getRowInstructions().setUseHeader(useHeader);
        return this;
    }

    @Override
    public CsvReader<T> setStartRow(int startRow) {
        getRowReader().getRowInstructions().setStartRow(startRow);
        return this;
    }

    @Override
    public CsvReader<T> setEscape(char symbol) {
        getRowReader().getRowInstructions().setEscape(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setQuote(char symbol) {
        getRowReader().getRowInstructions().setQuote(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setSeparator(char symbol) {
        getRowReader().getRowInstructions().setSeparator(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setComment(char symbol) {
        getRowReader().getRowInstructions().setComment(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setEndOfLine(char[] symbols) {
        getRowReader().getRowInstructions().setEndOfLine(symbols);
        return this;
    }

    @Override
    public CsvReader<T> skipEmptyLines(boolean skip) {
        getRowReader().getRowInstructions().skipEmptyLines(skip);
        return this;
    }

    @Override
    public CsvReader<T> skipCommentLines(boolean skip) {
        getRowReader().getRowInstructions().skipCommentLines(skip);
        return this;
    }

    @Override
    public CsvReader<T> setMapper(Class<? extends AbstractMapper> mapper) {
        getBeanReader().getBeanInstructions().setMapper(mapper);
        return this;
    }

    @Override
    public CsvReader<T> setDate(String propertyName, String dateFormat) {
        getBeanReader().getBeanInstructions().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public CsvReader<T> setLocalizedNumber(String propertyName, Locale locale) {
        getBeanReader().getBeanInstructions().setLocalizedNumber(propertyName, locale);
        return this;
    }

    @Override
    public CsvReader<T> setRequired(String propertyName, boolean required) {
        getBeanReader().getBeanInstructions().setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvReader<T> setConverter(String propertyName, Converter converter) {
        getBeanReader().getBeanInstructions().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public CsvReader<T> ignoreProperty(String propertyName) {
        getBeanReader().getBeanInstructions().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        getBeanReader().getBeanInstructions().mapColumnIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnNameToProperty(String columnName, String propertyName) {
        getBeanReader().getBeanInstructions().mapColumnNameToProperty(columnName, propertyName);
        return this;
    }

    @Override
    public CsvReader<T> setStartIndexDynamicColumns(int startIndex) {
        getBeanReader().getBeanInstructions().setStartIndexDynamicColumns(startIndex);
        return this;
    }

    @Override
    public CsvReader<T> setHeaderNameToProperty(String propertyName) {
        getBeanReader().getBeanInstructions().setHeaderNameToProperty(propertyName);
        return this;
    }

    @Override
    public CsvReader<T> setHeaderValueToProperty(String propertyName) {
        getBeanReader().getBeanInstructions().setHeaderValueToProperty(propertyName);
        return this;
    }

    private BeanReader<T> getBeanReader() {
        if (this.beanReader == null) {
            throw new CsvException(new GeneralError(
                    "BeanReader has not been initialized. Make sure to pass BeanInstructions or the bean class to CsvReader."
            ));
        }
        return this.beanReader;
    }

    private RowReader getRowReader() {
        return this.rowReader;
    }

}
