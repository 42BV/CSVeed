package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.line.LineReaderInstructions;
import nl.tweeenveertig.csveed.line.LineReaderInstructionsImpl;
import nl.tweeenveertig.csveed.report.CsvException;

import java.beans.PropertyEditor;

public class BeanReaderInstructionsImpl<T> implements BeanReaderInstructions<T> {

    private LineReaderInstructions lineReaderInstructions = new LineReaderInstructionsImpl();

    private BeanProperties properties;

    private Class<T> beanClass;

    private Class<? extends AbstractMapper> mappingStrategy = ColumnIndexMapper.class;

    public BeanReaderInstructionsImpl(Class<T> beanClass) {
        this.beanClass = beanClass;
        this.properties = new BeanProperties(beanClass);
    }

    public Class<T> getBeanClass() {
        return this.beanClass;
    }

    public T newInstance() {
        try {
            return this.beanClass.newInstance();
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    public LineReaderInstructions getLineReaderInstructions() {
        return this.lineReaderInstructions;
    }

    public BeanReaderInstructions<T> setUseHeader(boolean useHeader) {
        this.lineReaderInstructions.setUseHeader(useHeader);
        return this;
    }

    public BeanReaderInstructions<T> setStartRow(int startRow) {
        this.lineReaderInstructions.setStartRow(startRow);
        return this;
    }

    public BeanReaderInstructions<T> setEscape(char symbol) {
        this.lineReaderInstructions.setEscape(symbol);
        return this;
    }

    public BeanReaderInstructions<T> setQuote(char symbol) {
        this.lineReaderInstructions.setQuote(symbol);
        return this;
    }

    public BeanReaderInstructions<T> setSeparator(char symbol) {
        this.lineReaderInstructions.setSeparator(symbol);
        return this;
    }

    public BeanReaderInstructions<T> setEndOfLine(char[] symbols) {
        this.lineReaderInstructions.setEndOfLine(symbols);
        return this;
    }

    public BeanReaderInstructions<T> setMapper(Class<? extends AbstractMapper> mapper) {
        this.mappingStrategy = mapper;
        return this;
    }

    public BeanReaderInstructions<T> setDate(String propertyName, String dateFormat) {
        this.getProperties().setDate(propertyName, dateFormat);
        return this;
    }

    public BeanReaderInstructions<T> setRequired(String propertyName, boolean required) {
        this.getProperties().setRequired(propertyName, required);
        return this;
    }

    public BeanReaderInstructions<T> setConverter(String propertyName, PropertyEditor converter) {
        this.getProperties().setConverter(propertyName, converter);
        return this;
    }

    public BeanReaderInstructions<T> ignoreProperty(String propertyName) {
        this.getProperties().ignoreProperty(propertyName);
        return this;
    }

    public BeanReaderInstructions<T> mapColumnIndexToProperty(int columnIndex, String propertyName) {
        this.getProperties().mapIndexToProperty(columnIndex, propertyName);
        return this;
    }

    public BeanReaderInstructions<T> mapColumnNameToProperty(String columnName, String propertyName) {
        this.getProperties().mapNameToProperty(columnName, propertyName);
        return this;
    }

    public Class<? extends AbstractMapper> getMappingStrategy() {
        return this.mappingStrategy;
    }

    public BeanProperties getProperties() {
        return this.properties;
    }

    @SuppressWarnings("unchecked")
    public AbstractMapper<T, Object> createMappingStrategy() {
        try {
            return this.mappingStrategy.newInstance();
        } catch (Exception err) {
            throw new CsvException("Unable to instantiate the mapping strategy", err);
        }
    }

}
