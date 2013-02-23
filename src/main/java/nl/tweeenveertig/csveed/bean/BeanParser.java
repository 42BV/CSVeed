package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.annotations.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
*
*/
public class BeanParser<T> {

    private BeanReaderInstructionsImpl<T> beanReaderInstructionsImpl;

    private int columnIndex = 0;

    public BeanReaderInstructionsImpl<T> getBeanInstructions(Class<T> beanClass) {

        this.beanReaderInstructionsImpl = new BeanReaderInstructionsImpl<T>(beanClass);

        Annotation[] annotations = beanClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvFile) {
                parseCsvFile((CsvFile)annotation);
            }
        }

        for (BeanProperty beanProperty : beanReaderInstructionsImpl.getProperties()) {
            checkForAnnotations(beanProperty);
        }

        return this.beanReaderInstructionsImpl;
    }

    public void checkForAnnotations(BeanProperty beanProperty) {

        Field currentField = beanProperty.getField();
        Annotation[] annotations = currentField.getDeclaredAnnotations();
        String propertyName = beanProperty.getPropertyName();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvCell) {
                parseCsvCell(propertyName, (CsvCell)annotation);
            } else if (annotation instanceof CsvConverter) {
                parseCsvConverter(propertyName, (CsvConverter)annotation);
            } else if (annotation instanceof CsvDate) {
                parseCsvDate(propertyName, (CsvDate)annotation);
            } else if (annotation instanceof CsvIgnore) {
                this.beanReaderInstructionsImpl.ignoreProperty(propertyName);
                return;
            }
        }
        this.beanReaderInstructionsImpl.mapIndexToProperty(columnIndex++, propertyName);
    }

    private void parseCsvFile(CsvFile csvFile) {

        this.beanReaderInstructionsImpl
            .setEscape(csvFile.escape())
            .setQuote(csvFile.quote())
            .setSeparator(csvFile.separator())
            .setEndOfLine(csvFile.endOfLine())
            .setMappingStrategy(csvFile.mappingStrategy())
            .setStartRow(csvFile.startRow())
            .setUseHeader(csvFile.useHeader());

    }

    private void parseCsvDate(String propertyName, CsvDate csvDate) {
        DateFormat dateFormat = new SimpleDateFormat(csvDate.format());
        this.beanReaderInstructionsImpl.setConverter(propertyName, new CustomDateEditor(dateFormat, true));
    }

    private void parseCsvConverter(String propertyName, CsvConverter csvConverter) {
        try {
            this.beanReaderInstructionsImpl.setConverter(propertyName, csvConverter.converter().newInstance());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    private void parseCsvCell(String propertyName, CsvCell csvCell) {
        String columnName = (csvCell.name() == null || csvCell.name().equals("")) ? propertyName : csvCell.name();
        this.beanReaderInstructionsImpl.mapNameToProperty(columnName, propertyName);
        this.beanReaderInstructionsImpl.setRequired(propertyName, csvCell.required());
        columnIndex = csvCell.indexColumn() != -1 ? csvCell.indexColumn() : columnIndex;
    }

}
