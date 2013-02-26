package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.bean.*;

import java.beans.PropertyEditor;
import java.io.Reader;
import java.util.List;

public class CsvReaderImpl<T> implements CsvReader<T> {

    private BeanReader<T> beanReader;

    private BeanReaderInstructions<T> beanReaderInstructions;

    public CsvReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser<T>().getBeanInstructions(beanClass));
    }

    public CsvReaderImpl(Reader reader, BeanReaderInstructions<T> beanReaderInstructions) {
        this.beanReaderInstructions = beanReaderInstructions;
        this.beanReader = new BeanReaderImpl<T>(reader, beanReaderInstructions);
    }

    @Override
    public List<T> readBeans() {
        return this.beanReader.readBeans();
    }

    @Override
    public T readBean() {
        return this.beanReader.readBean();
    }

    @Override
    public List<Row> readLines() {
        return this.beanReader.getLineReader().readLines();
    }

    @Override
    public Row readLine() {
        return this.beanReader.getLineReader().readLine();
    }

    @Override
    public int getCurrentLine() {
        return this.beanReader.getCurrentLine();
    }

    @Override
    public boolean isFinished() {
        return this.beanReader.isFinished();
    }

    @Override
    public CsvReader<T> setUseHeader(boolean useHeader) {
        this.beanReaderInstructions.setUseHeader(useHeader);
        return this;
    }

    @Override
    public CsvReader<T> setStartRow(int startRow) {
        this.beanReaderInstructions.setStartRow(startRow);
        return this;
    }

    @Override
    public CsvReader<T> setEscape(char symbol) {
        this.beanReaderInstructions.setEscape(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setQuote(char symbol) {
        this.beanReaderInstructions.setQuote(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setSeparator(char symbol) {
        this.beanReaderInstructions.setSeparator(symbol);
        return this;
    }

    @Override
    public CsvReader<T> setEndOfLine(char[] symbols) {
        this.beanReaderInstructions.setEndOfLine(symbols);
        return this;
    }

    @Override
    public CsvReader<T> setMapper(Class<? extends AbstractMapper> mapper) {
        this.beanReaderInstructions.setMapper(mapper);
        return this;
    }

    @Override
    public CsvReader<T> setRequired(String propertyName, boolean required) {
        this.beanReaderInstructions.setRequired(propertyName, required);
        return this;
    }

    @Override
    public CsvReader<T> setConverter(String propertyName, PropertyEditor converter) {
        this.beanReaderInstructions.setConverter(propertyName, converter);
        return this;
    }

    @Override
    public CsvReader<T> ignoreProperty(String propertyName) {
        this.beanReaderInstructions.ignoreProperty(propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        this.beanReaderInstructions.mapColumnIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public CsvReader<T> mapColumnNameToProperty(String columnName, String propertyName) {
        this.beanReaderInstructions.mapColumnNameToProperty(columnName, propertyName);
        return this;
    }

}
