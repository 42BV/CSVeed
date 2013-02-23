package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.reader.AbstractMappingStrategy;
import nl.tweeenveertig.csveed.reader.ColumnIndexStrategy;
import nl.tweeenveertig.csveed.report.CsvException;

import java.util.ArrayList;
import java.util.List;

public class BeanInstructions<T> {

    private CsvInstructions csvInstructions = new CsvInstructions();

    private List<BeanProperty> properties = new ArrayList<BeanProperty>();

    private Class<T> beanClass;

    private Class<? extends AbstractMappingStrategy> mappingStrategy = ColumnIndexStrategy.class;

    public BeanInstructions(Class<T> beanClass) {
        this.beanClass = beanClass;
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

    public Class<? extends AbstractMappingStrategy> getMappingStrategy() {
        return this.mappingStrategy;
    }

    public void addProperty(BeanProperty beanProperty) {
        this.properties.add(beanProperty);
    }

    public List<BeanProperty> getProperties() {
        return this.properties;
    }

    public BeanProperty getBeanPropertyWithName(String name) {
        for (BeanProperty beanProperty : getProperties()) {
            if (beanProperty.getName().equals(name)) {
                return beanProperty;
            }
        }
        return null;
    }

    public BeanProperty getBeanPropertyWithIndex(int indexColumn) {
        for (BeanProperty beanProperty : getProperties()) {
            if (beanProperty.getIndexColumn() == indexColumn) {
                return beanProperty;
            }
        }
        return null;
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
