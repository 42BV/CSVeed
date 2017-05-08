package org.csveed.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.csveed.bean.conversion.Converter;
import org.csveed.common.Column;
import org.csveed.row.RowInstructions;
import org.csveed.row.RowInstructionsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanInstructionsImpl implements BeanInstructions {

    public static final Logger LOG = LoggerFactory.getLogger(BeanInstructionsImpl.class);

    private RowInstructions rowInstructions = new RowInstructionsImpl();

    private BeanProperties properties;

    private Class<? extends AbstractMapper> mappingStrategy = ColumnIndexMapper.class;

    private Class beanClass;

    private boolean settingsLogged = false;

    private Column startIndexDynamicColumns = null;

    private boolean useHeader = true;

    public BeanInstructionsImpl(Class beanClass) {
        this.properties = new BeanProperties(beanClass);
        this.beanClass = beanClass;
    }

    @Override
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
            lineParts.add("Column index " + property.getColumnIndex() + " -> property [" + property.getPropertyName() + "]");
        } else if (mappingStrategy.equals(ColumnNameMapper.class)) {
            lineParts.add("Column name [" + property.getColumnName() + "] -> property [" + property.getPropertyName() + "]");
        }
        lineParts.add("Required: " + (property.isRequired() ? "yes" : "no"));
        if (property.getConverter() != null) {
            lineParts.add("Converter: " + property.getConverter().getClass().getSimpleName() + " for " + property.getPropertyType());
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

    @Override
    public RowInstructions getRowInstructions() {
        return this.rowInstructions;
    }

    @Override
    public BeanInstructions setUseHeader(boolean useHeader) {
        this.rowInstructions.setUseHeader(useHeader);
        this.useHeader = useHeader;
        return this;
    }

    @Override
    public BeanInstructions setStartRow(int startRow) {
        this.rowInstructions.setStartRow(startRow);
        return this;
    }

    @Override
    public BeanInstructions setEscape(char symbol) {
        this.rowInstructions.setEscape(symbol);
        return this;
    }

    @Override
    public BeanInstructions setQuote(char symbol) {
        this.rowInstructions.setQuote(symbol);
        return this;
    }

    @Override
    public BeanInstructions setQuotingEnabled(boolean enabled) {
        this.rowInstructions.setQuotingEnabled(enabled);
        return this;
    }

    @Override
    public BeanInstructions setSeparator(char symbol) {
        this.rowInstructions.setSeparator(symbol);
        return this;
    }

    @Override
    public BeanInstructions setComment(char symbol) {
        this.rowInstructions.setComment(symbol);
        return this;
    }

    @Override
    public BeanInstructions setEndOfLine(char[] symbols) {
        this.rowInstructions.setEndOfLine(symbols);
        return this;
    }

    @Override
    public BeanInstructions skipEmptyLines(boolean skip) {
        this.rowInstructions.skipEmptyLines(skip);
        return this;
    }

    @Override
    public BeanInstructions skipCommentLines(boolean skip) {
        this.rowInstructions.skipCommentLines(skip);
        return this;
    }

    @Override
    public BeanInstructions setMapper(Class<? extends AbstractMapper> mapper) {
        this.mappingStrategy = mapper;
        return this;
    }

    @Override
    public BeanInstructions setDate(String propertyName, String dateFormat) {
        this.getProperties().setDate(propertyName, dateFormat);
        return this;
    }

    @Override
    public BeanInstructions setLocalizedNumber(String propertyName, Locale locale) {
        this.getProperties().setLocalizedNumber(propertyName, locale);
        return this;
    }

    @Override
    public BeanInstructions setRequired(String propertyName, boolean required) {
        this.getProperties().setRequired(propertyName, required);
        return this;
    }

    @Override
    public BeanInstructions setConverter(String propertyName, Converter converter) {
        this.getProperties().setConverter(propertyName, converter);
        return this;
    }

    @Override
    public BeanInstructions ignoreProperty(String propertyName) {
        this.getProperties().ignoreProperty(propertyName);
        return this;
    }

    @Override
    public BeanInstructions mapColumnIndexToProperty(int columnIndex, String propertyName) {
        this.getProperties().mapIndexToProperty(columnIndex, propertyName);
        return this;
    }

    @Override
    public BeanInstructions mapColumnNameToProperty(String columnName, String propertyName) {
        this.getProperties().mapNameToProperty(columnName, propertyName);
        return this;
    }

    @Override
    public BeanInstructions setHeaderNameToProperty(String propertyName) {
        this.getProperties().setHeaderNameProperty(propertyName);
        return this;
    }

    @Override
    public BeanInstructions setHeaderValueToProperty(String propertyName) {
        this.getProperties().setHeaderValueProperty(propertyName);
        return this;
    }

    @Override
    public BeanInstructions setStartIndexDynamicColumns(int startIndex) {
        this.startIndexDynamicColumns = startIndex == 0 ? null : new Column(startIndex);
        return this;
    }

    @Override
    public Class<? extends AbstractMapper> getMappingStrategy() {
        return this.mappingStrategy;
    }

    @Override
    public BeanProperties getProperties() {
        return this.properties;
    }

    @Override
    public Column getStartIndexDynamicColumns() {
        return this.startIndexDynamicColumns;
    }

    @Override
    public Class getBeanClass() {
        return this.beanClass;
    }

    @Override
    public boolean useHeader() {
        return useHeader;
    }

}
