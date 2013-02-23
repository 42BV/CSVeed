package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.bean.AbstractMappingStrategy;

import java.beans.PropertyEditor;

public interface BeanReaderInstructions<T> {

    BeanReaderInstructions<T> setUseHeader(boolean useHeader);

    BeanReaderInstructions<T> setStartRow(int startRow);

    BeanReaderInstructions<T> setEscape(char symbol);

    BeanReaderInstructions<T> setQuote(char symbol);

    BeanReaderInstructions<T> setSeparator(char symbol);

    BeanReaderInstructions<T> setEndOfLine(char[] symbols);

    BeanReaderInstructions<T> setMappingStrategy(Class<? extends AbstractMappingStrategy> mappingStrategy);

    BeanReaderInstructions<T> setRequired(String propertyName, boolean required);

    BeanReaderInstructions<T> setConverter(String propertyName, PropertyEditor converter);

    BeanReaderInstructions<T> ignoreProperty(String propertyName);

    BeanReaderInstructions<T> mapIndexToProperty(int columnIndex, String propertyName);

    BeanReaderInstructions<T> mapNameToProperty(String columnName, String propertyName);

}
