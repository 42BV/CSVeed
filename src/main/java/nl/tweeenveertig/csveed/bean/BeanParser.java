package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.annotations.*;
import nl.tweeenveertig.csveed.api.BeanReaderInstructions;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BeanParser<T> {

    private BeanReaderInstructions<T> beanReaderInstructions;

    private int columnIndex = 0;

    public BeanReaderInstructions<T> getBeanInstructions(Class<T> beanClass) {

        this.beanReaderInstructions = new BeanReaderInstructionsImpl<T>(beanClass);

        Annotation[] annotations = beanClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvFile) {
                parseCsvFile((CsvFile)annotation);
            }
        }

        for (BeanProperty beanProperty : ((BeanReaderInstructionsImpl<T>)beanReaderInstructions).getProperties()) {
            checkForAnnotations(beanProperty);
        }

        return this.beanReaderInstructions;
    }

    public void checkForAnnotations(BeanProperty beanProperty) {

        Field currentField = beanProperty.getField();
        Annotation[] annotations = currentField.getDeclaredAnnotations();
        String propertyName = beanProperty.getPropertyName();
        String columnName = propertyName;
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvCell) {
                columnName = parseCsvCell(propertyName, (CsvCell)annotation);
            } else if (annotation instanceof CsvConverter) {
                parseCsvConverter(propertyName, (CsvConverter)annotation);
            } else if (annotation instanceof CsvDate) {
                parseCsvDate(propertyName, (CsvDate)annotation);
            } else if (annotation instanceof CsvIgnore) {
                this.beanReaderInstructions.ignoreProperty(propertyName);
                return;
            }
        }
        this.beanReaderInstructions.mapColumnNameToProperty(columnName, propertyName);
        this.beanReaderInstructions.mapColumnIndexToProperty(columnIndex++, propertyName);
    }

    private void parseCsvFile(CsvFile csvFile) {

        this.beanReaderInstructions
            .setEscape(csvFile.escape())
            .setQuote(csvFile.quote())
            .setSeparator(csvFile.separator())
            .setEndOfLine(csvFile.endOfLine())
            .setMapper(csvFile.mappingStrategy())
            .setStartRow(csvFile.startRow())
            .setUseHeader(csvFile.useHeader());

    }

    private void parseCsvDate(String propertyName, CsvDate csvDate) {
        DateFormat dateFormat = new SimpleDateFormat(csvDate.format());
        this.beanReaderInstructions.setConverter(propertyName, new CustomDateEditor(dateFormat, true));
    }

    private void parseCsvConverter(String propertyName, CsvConverter csvConverter) {
        try {
            this.beanReaderInstructions.setConverter(propertyName, csvConverter.converter().newInstance());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    private String parseCsvCell(String propertyName, CsvCell csvCell) {
        String columnName = (csvCell.name() == null || csvCell.name().equals("")) ? propertyName : csvCell.name();
        this.beanReaderInstructions.setRequired(propertyName, csvCell.required());
        columnIndex = csvCell.indexColumn() != -1 ? csvCell.indexColumn() : columnIndex;
        return columnName;
    }

}
