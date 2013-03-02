package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.row.RowReaderInstructions;
import nl.tweeenveertig.csveed.row.RowReaderInstructionsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditor;

public class BeanReaderInstructionsImpl implements BeanReaderInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(BeanReaderInstructionsImpl.class);

    private RowReaderInstructions rowReaderInstructions = new RowReaderInstructionsImpl();

    private BeanProperties properties;

    private Class<? extends AbstractMapper> mappingStrategy = ColumnIndexMapper.class;

    private Class beanClass;

    private boolean settingsLogged = false;

    public BeanReaderInstructionsImpl(Class beanClass) {
        this.properties = new BeanProperties(beanClass);
        this.beanClass = beanClass;
    }

    public void logSettings() {
        if (settingsLogged) {
            return;
        }
        LOG.info("- CSV config / mapping strategy: " + this.getMappingStrategy());
        settingsLogged = true;
    }

    public RowReaderInstructions getRowReaderInstructions() {
        return this.rowReaderInstructions;
    }

    @Override
    public BeanReaderInstructions setUseHeader(boolean useHeader) {
        this.rowReaderInstructions.setUseHeader(useHeader);
        return this;
    }

    @Override
    public BeanReaderInstructions setStartRow(int startRow) {
        this.rowReaderInstructions.setStartRow(startRow);
        return this;
    }

    @Override
    public BeanReaderInstructions setEscape(char symbol) {
        this.rowReaderInstructions.setEscape(symbol);
        return this;
    }

    @Override
    public BeanReaderInstructions setQuote(char symbol) {
        this.rowReaderInstructions.setQuote(symbol);
        return this;
    }

    @Override
    public BeanReaderInstructions setSeparator(char symbol) {
        this.rowReaderInstructions.setSeparator(symbol);
        return this;
    }

    @Override
    public BeanReaderInstructions setComment(char symbol) {
        this.rowReaderInstructions.setComment(symbol);
        return this;
    }

    @Override
    public BeanReaderInstructions setEndOfLine(char[] symbols) {
        this.rowReaderInstructions.setEndOfLine(symbols);
        return this;
    }

    @Override
    public BeanReaderInstructions skipEmptyLines(boolean skip) {
        this.rowReaderInstructions.skipEmptyLines(skip);
        return this;
    }

    @Override
    public BeanReaderInstructions skipCommentLines(boolean skip) {
        this.rowReaderInstructions.skipCommentLines(skip);
        return this;
    }

    @Override
    public BeanReaderInstructions setMapper(Class<? extends AbstractMapper> mapper) {
        this.mappingStrategy = mapper;
        return this;
    }

    @Override
    public BeanReaderInstructions setDate(String propertyName, String dateFormat) {
        this.getProperties().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public BeanReaderInstructions setRequired(String propertyName, boolean required) {
        this.getProperties().setRequired(propertyName, required);
        return this;
    }

    @Override
    public BeanReaderInstructions setConverter(String propertyName, PropertyEditor converter) {
        this.getProperties().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public BeanReaderInstructions ignoreProperty(String propertyName) {
        this.getProperties().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public BeanReaderInstructions mapColumnIndexToProperty(int columnIndex, String propertyName) {
        this.getProperties().mapIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public BeanReaderInstructions mapColumnNameToProperty(String columnName, String propertyName) {
        this.getProperties().mapNameToProperty(columnName, propertyName);
        return this;
    }

    public Class<? extends AbstractMapper> getMappingStrategy() {
        return this.mappingStrategy;
    }

    public BeanProperties getProperties() {
        return this.properties;
    }

    public Class getBeanClass() {
        return this.beanClass;
    }

}
