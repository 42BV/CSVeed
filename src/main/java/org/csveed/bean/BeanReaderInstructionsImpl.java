package org.csveed.bean;

import org.csveed.bean.conversion.Converter;
import org.csveed.row.RowReaderInstructions;
import org.csveed.row.RowReaderInstructionsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        for (BeanProperty property : properties) {
            LOG.info(getPropertyLogLine(property));
        }
        settingsLogged = true;
    }

    protected String getPropertyLogLine(BeanProperty property) {
        List<String> lineParts = new ArrayList<String>();
        lineParts.add("- CSV config");
        if (mappingStrategy.equals(ColumnIndexMapper.class)) {
            lineParts.add("Column index "+property.getColumnIndex()+" -> property ["+property.getPropertyName()+"]");
        } else if (mappingStrategy.equals(ColumnNameMapper.class)) {
            lineParts.add("Column name ["+property.getColumnName()+"] -> property ["+property.getPropertyName()+"]");
        }
        lineParts.add("Required: "+(property.isRequired()?"yes":"no"));
        if (property.getConverter() != null) {
            lineParts.add("Converter: "+property.getConverter().getClass().getSimpleName()+" for "+property.getPropertyType());
        }

        StringBuilder logLine = new StringBuilder();
        boolean first = true;
        for (String linePart : lineParts) {
            if (!first) {
                logLine.append(" | ");
            }
            logLine.append(linePart);
            first = false;
        }
        return logLine.toString();
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
    public BeanReaderInstructions setConverter(String propertyName, Converter converter) {
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
