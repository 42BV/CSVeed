package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class BeanParser {

    private BeanReaderInstructions beanReaderInstructions;

    private int columnIndex = 0;

    public BeanReaderInstructions getBeanInstructions(Class beanClass) {

        this.beanReaderInstructions = new BeanReaderInstructionsImpl(beanClass);

        Annotation[] annotations = beanClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvFile) {
                parseCsvFile((CsvFile)annotation);
            }
        }

        for (BeanProperty beanProperty : ((BeanReaderInstructionsImpl)beanReaderInstructions).getProperties()) {
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
            .setUseHeader(csvFile.useHeader())
            .skipEmptyLines(csvFile.skipEmptyLines())
            .skipCommentLines(csvFile.skipCommentLines());

    }

    private void parseCsvDate(String propertyName, CsvDate csvDate) {
        this.beanReaderInstructions.setDate(propertyName, csvDate.format());
    }

    private void parseCsvConverter(String propertyName, CsvConverter csvConverter) {
        try {
            this.beanReaderInstructions.setConverter(propertyName, csvConverter.converter().newInstance());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    private String parseCsvCell(String propertyName, CsvCell csvCell) {
        String columnName = (csvCell.columnName() == null || csvCell.columnName().equals("")) ? propertyName : csvCell.columnName();
        this.beanReaderInstructions.setRequired(propertyName, csvCell.required());
        columnIndex = csvCell.columnIndex() != -1 ? csvCell.columnIndex() : columnIndex;
        return columnName;
    }

}
