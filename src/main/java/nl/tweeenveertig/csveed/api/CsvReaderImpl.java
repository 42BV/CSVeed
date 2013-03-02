package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.bean.*;
import nl.tweeenveertig.csveed.line.Header;
import nl.tweeenveertig.csveed.line.RowReaderImpl;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;
import java.io.Reader;
import java.util.List;

public class CsvReaderImpl<T> implements CsvReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(CsvReaderImpl.class);

    private BeanReaderImpl<T> beanReader;

    private RowReaderImpl lineReader;

    public CsvReaderImpl(Reader reader) {
        this.lineReader = new RowReaderImpl(reader);
    }
    
    public CsvReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public CsvReaderImpl(Reader reader, BeanReaderInstructions beanReaderInstructions) {
        this.beanReader = new BeanReaderImpl<T>(reader, beanReaderInstructions);
        this.lineReader = (RowReaderImpl)getBeanReader().getRowReader();
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
        return getLineReader().readRows();
    }

    @Override
    public Row readRow() {
        return getLineReader().readRow();
    }

    @Override
    public Header readHeader() {
        return getLineReader().readHeader();
    }

    @Override
    public int getCurrentLine() {
        return getLineReader().getCurrentLine();
    }

    @Override
    public boolean isFinished() {
        return getLineReader().isFinished();
    }

    @Override
    public CsvReader<T> setUseHeader(boolean useHeader) {
        getLineReader().getLineReaderInstructions().setUseHeader(useHeader);
        return this;
    }

    @Override
    public CsvReader<T> setStartRow(int startRow) {
        getLineReader().getLineReaderInstructions().setStartRow(startRow);
        return this;
    }

    @Override
    public CsvReader<T> setEscape(char symbol) {
        getLineReader().getLineReaderInstructions().setEscape(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setQuote(char symbol) {
        getLineReader().getLineReaderInstructions().setQuote(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setSeparator(char symbol) {
        getLineReader().getLineReaderInstructions().setSeparator(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setComment(char symbol) {
        getLineReader().getLineReaderInstructions().setComment(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setEndOfLine(char[] symbols) {
        getLineReader().getLineReaderInstructions().setEndOfLine(symbols);
        return this;
    }

    @Override
    public CsvReader<T> skipEmptyLines(boolean skip) {
        getLineReader().getLineReaderInstructions().skipEmptyLines(skip);
        return this;
    }

    @Override
    public CsvReader<T> skipCommentLines(boolean skip) {
        getLineReader().getLineReaderInstructions().skipCommentLines(skip);
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
    public CsvReader<T> setRequired(String propertyName, boolean required) {
        getBeanReader().getBeanReaderInstructions().setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvReader<T> setConverter(String propertyName, PropertyEditor converter) {
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
    
    private BeanReaderImpl<T> getBeanReader() {
        if (this.beanReader == null) {
            String msg = "BeanReader has not been initialized. Make sure to pass BeanReaderInstructions or the bean class to CsvReader.";
            LOG.error(msg);
            throw new CsvException(msg);
        }
        return this.beanReader;
    }

    private RowReaderImpl getLineReader() {
        return this.lineReader;
    }

}
