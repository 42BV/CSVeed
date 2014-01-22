package org.csveed.api;

import org.csveed.bean.*;
import org.csveed.bean.conversion.Converter;
import org.csveed.report.GeneralError;
import org.csveed.row.HeaderImpl;
import org.csveed.row.RowReaderImpl;
import org.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.List;
import java.util.Locale;

public class CsvReaderImpl<T> implements CsvReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(CsvReaderImpl.class);

    private BeanReaderImpl<T> beanReader;

    private RowReaderImpl rowReader;

    public CsvReaderImpl(Reader reader) {
        this.rowReader = new RowReaderImpl(reader);
    }
    
    public CsvReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public CsvReaderImpl(Reader reader, BeanReaderInstructions beanReaderInstructions) {
        this.beanReader = new BeanReaderImpl<T>(reader, beanReaderInstructions);
        this.rowReader = (RowReaderImpl)getBeanReader().getRowReader();
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
    public HeaderImpl readHeader() {
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
        getRowReader().getRowReaderInstructions().setUseHeader(useHeader);
        return this;
    }

    @Override
    public CsvReader<T> setStartRow(int startRow) {
        getRowReader().getRowReaderInstructions().setStartRow(startRow);
        return this;
    }

    @Override
    public CsvReader<T> setEscape(char symbol) {
        getRowReader().getRowReaderInstructions().setEscape(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setQuote(char symbol) {
        getRowReader().getRowReaderInstructions().setQuote(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setSeparator(char symbol) {
        getRowReader().getRowReaderInstructions().setSeparator(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setComment(char symbol) {
        getRowReader().getRowReaderInstructions().setComment(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setEndOfLine(char[] symbols) {
        getRowReader().getRowReaderInstructions().setEndOfLine(symbols);
        return this;
    }

    @Override
    public CsvReader<T> skipEmptyLines(boolean skip) {
        getRowReader().getRowReaderInstructions().skipEmptyLines(skip);
        return this;
    }

    @Override
    public CsvReader<T> skipCommentLines(boolean skip) {
        getRowReader().getRowReaderInstructions().skipCommentLines(skip);
        return this;
    }

    @Override
    public CsvReader<T> setMapper(Class<? extends AbstractMapper> mapper) {
        getBeanReader().getBeanReaderInstructions().setMapper(mapper);
        return this;
    }

    @Override
    public CsvReader<T> setDate(String propertyName, String dateFormat) {
        getBeanReader().getBeanReaderInstructions().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public CsvReader<T> setLocalizedNumber(String propertyName, Locale locale) {
        getBeanReader().getBeanReaderInstructions().setLocalizedNumber(propertyName, locale);
        return this;
    }

    @Override
    public CsvReader<T> setRequired(String propertyName, boolean required) {
        getBeanReader().getBeanReaderInstructions().setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvReader<T> setConverter(String propertyName, Converter converter) {
        getBeanReader().getBeanReaderInstructions().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public CsvReader<T> ignoreProperty(String propertyName) {
        getBeanReader().getBeanReaderInstructions().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        getBeanReader().getBeanReaderInstructions().mapColumnIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnNameToProperty(String columnName, String propertyName) {
        getBeanReader().getBeanReaderInstructions().mapColumnNameToProperty(columnName, propertyName);
        return this;
    }

    @Override
    public CsvReader<T> setStartIndexDynamicColumns(int startIndex) {
        getBeanReader().getBeanReaderInstructions().setStartIndexDynamicColumns(startIndex);
        return this;
    }

    private BeanReaderImpl<T> getBeanReader() {
        if (this.beanReader == null) {
            throw new CsvException(new GeneralError(
                    "BeanReader has not been initialized. Make sure to pass BeanReaderInstructions or the bean class to CsvReader."
            ));
        }
        return this.beanReader;
    }

    private RowReaderImpl getRowReader() {
        return this.rowReader;
    }

}
