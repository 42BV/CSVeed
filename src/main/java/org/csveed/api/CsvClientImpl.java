package org.csveed.api;

import org.csveed.bean.*;
import org.csveed.bean.conversion.Converter;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.row.*;

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CsvClientImpl<T> implements CsvClient<T> {

    private BeanReader<T> beanReader;

    private RowReader rowReader;

    private RowWriter rowWriter;
    
    private final RowInstructions rowInstructions;

    public CsvClientImpl(Writer writer) {
        this.rowWriter = new RowWriterImpl(writer);
        this.rowInstructions = this.rowWriter.getRowInstructions();
    }

    public CsvClientImpl(Reader reader) {
        this.rowReader = new RowReaderImpl(reader);
        this.rowInstructions = this.rowReader.getRowInstructions();
    }
    
    public CsvClientImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public CsvClientImpl(Reader reader, BeanInstructions beanInstructions) {
        this.beanReader = new BeanReaderImpl<T>(reader, beanInstructions);
        this.rowReader = getBeanReader().getRowReader();
        this.rowInstructions = this.rowReader.getRowInstructions();
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

    public Header writeHeader(String[] header) {
        return getRowWriter().writeHeader(header);
    }

    public void writeHeader(Header header) {
        getRowWriter().writeHeader(header);
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
        getBeanReader().getBeanInstructions().setMapper(mapper);
        return this;
    }

    @Override
    public CsvClient<T> setDate(String propertyName, String dateFormat) {
        getBeanReader().getBeanInstructions().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public CsvClient<T> setLocalizedNumber(String propertyName, Locale locale) {
        getBeanReader().getBeanInstructions().setLocalizedNumber(propertyName, locale);
        return this;
    }

    @Override
    public CsvClient<T> setRequired(String propertyName, boolean required) {
        getBeanReader().getBeanInstructions().setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvClient<T> setConverter(String propertyName, Converter converter) {
        getBeanReader().getBeanInstructions().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public CsvClient<T> ignoreProperty(String propertyName) {
        getBeanReader().getBeanInstructions().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public CsvClient<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        getBeanReader().getBeanInstructions().mapColumnIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public CsvClient<T> mapColumnNameToProperty(String columnName, String propertyName) {
        getBeanReader().getBeanInstructions().mapColumnNameToProperty(columnName, propertyName);
        return this;
    }

    @Override
    public CsvClient<T> setStartIndexDynamicColumns(int startIndex) {
        getBeanReader().getBeanInstructions().setStartIndexDynamicColumns(startIndex);
        return this;
    }

    @Override
    public CsvClient<T> setHeaderNameToProperty(String propertyName) {
        getBeanReader().getBeanInstructions().setHeaderNameToProperty(propertyName);
        return this;
    }

    @Override
    public CsvClient<T> setHeaderValueToProperty(String propertyName) {
        getBeanReader().getBeanInstructions().setHeaderValueToProperty(propertyName);
        return this;
    }

    private BeanReader<T> getBeanReader() {
        if (this.beanReader == null) {
            throw new CsvException(new GeneralError(
                    "BeanReader has not been initialized. Make sure to pass BeanInstructions or the bean class to CsvClient."
            ));
        }
        return this.beanReader;
    }

    private RowReader getRowReader() {
        return this.rowReader;
    }

    public RowWriter getRowWriter() {
        return rowWriter;
    }

}
