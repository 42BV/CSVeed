package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.line.CsvInstructions;
import nl.tweeenveertig.csveed.report.CsvException;

import java.beans.PropertyEditor;

public class BeanInstructions<T> {

    private CsvInstructions csvInstructions = new CsvInstructions();

    private BeanProperties properties;

    private Class<T> beanClass;

    private Class<? extends AbstractMappingStrategy> mappingStrategy = ColumnIndexStrategy.class;

    public BeanInstructions(Class<T> beanClass) {
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

    public CsvInstructions getCsvInstructions() {
        return this.csvInstructions;
    }

    public BeanInstructions<T> setUseHeader(boolean useHeader) {
        this.csvInstructions.setUseHeader(useHeader);
        return this;
    }

    public BeanInstructions<T> setStartRow(int startRow) {
        this.csvInstructions.setStartRow(startRow);
        return this;
    }

    public BeanInstructions<T> setEscape(char symbol) {
        this.csvInstructions.setEscape(symbol);
        return this;
    }

    public BeanInstructions<T> setQuote(char symbol) {
        this.csvInstructions.setQuote(symbol);
        return this;
    }

    public BeanInstructions<T> setSeparator(char symbol) {
        this.csvInstructions.setSeparator(symbol);
        return this;
    }

    public BeanInstructions<T> setEndOfLine(char[] symbols) {
        this.csvInstructions.setEndOfLine(symbols);
        return this;
    }

    public BeanInstructions<T> setMappingStrategy(Class<? extends AbstractMappingStrategy> mappingStrategy) {
        this.mappingStrategy = mappingStrategy;
        return this;
    }

    public BeanInstructions<T> setRequired(String propertyName, boolean required) {
        this.getProperties().setRequired(propertyName, required);
        return this;
    }

    public BeanInstructions<T> setConverter(String propertyName, PropertyEditor converter) {
        this.getProperties().setConverter(propertyName, converter);
        return this;
    }

    public BeanInstructions<T> ignoreProperty(String propertyName) {
        this.getProperties().ignoreProperty(propertyName);
        return this;
    }

    public BeanInstructions<T> mapIndexToProperty(int columnIndex, String propertyName) {
        this.getProperties().mapIndexToProperty(columnIndex, propertyName);
        return this;
    }

    public BeanInstructions<T> mapNameToProperty(String columnName, String propertyName) {
        this.getProperties().mapNameToProperty(columnName, propertyName);
        return this;
    }

    public Class<? extends AbstractMappingStrategy> getMappingStrategy() {
        return this.mappingStrategy;
    }

    public BeanProperties getProperties() {
        return this.properties;
    }

    public BeanProperty getBeanPropertyWithName(String columnName) {
        return this.properties.fromName(columnName);
    }

    public BeanProperty getBeanPropertyWithIndex(int columnIndex) {
        return this.properties.fromIndex(columnIndex);
    }

    @SuppressWarnings("unchecked")
    public AbstractMappingStrategy<T> createMappingStrategy() {
        try {
            return this.mappingStrategy.newInstance();
        } catch (Exception err) {
            throw new CsvException("Unable to instantiate the mapping strategy", err);
        }
    }

}
