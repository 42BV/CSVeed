/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvDate;
import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvHeaderName;
import org.csveed.annotations.CsvHeaderValue;
import org.csveed.annotations.CsvIgnore;
import org.csveed.annotations.CsvLocalizedNumber;
import org.csveed.common.Column;

/**
 * The Class BeanParser.
 */
public class BeanParser {

    /** The bean instructions. */
    private BeanInstructions beanInstructions;

    /** The current column. */
    private Column currentColumn = new Column();

    /**
     * Gets the bean instructions.
     *
     * @param beanClass
     *            the bean class
     *
     * @return the bean instructions
     */
    public BeanInstructions getBeanInstructions(Class beanClass) {

        this.beanInstructions = new BeanInstructionsImpl(beanClass);

        Annotation[] annotations = beanClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvFile) {
                parseCsvFile((CsvFile) annotation);
            }
        }

        for (BeanProperty beanProperty : beanInstructions.getProperties()) {
            checkForAnnotations(beanProperty);
        }

        return this.beanInstructions;
    }

    /**
     * Check for annotations.
     *
     * @param beanProperty
     *            the bean property
     */
    public void checkForAnnotations(BeanProperty beanProperty) {

        Field currentField = beanProperty.getField();
        Annotation[] annotations = currentField.getDeclaredAnnotations();
        String propertyName = beanProperty.getPropertyName();
        String columnName = propertyName;
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvCell) {
                columnName = parseCsvCell(propertyName, (CsvCell) annotation);
            } else if (annotation instanceof CsvConverter) {
                parseCsvConverter(propertyName, (CsvConverter) annotation);
            } else if (annotation instanceof CsvDate) {
                parseCsvDate(propertyName, (CsvDate) annotation);
            } else if (annotation instanceof CsvLocalizedNumber) {
                parseCsvLocalizedNumber(propertyName, (CsvLocalizedNumber) annotation);
            } else if (annotation instanceof CsvHeaderName) {
                this.beanInstructions.setHeaderNameToProperty(propertyName);
            } else if (annotation instanceof CsvHeaderValue) {
                this.beanInstructions.setHeaderValueToProperty(propertyName);
            } else if (annotation instanceof CsvIgnore) {
                this.beanInstructions.ignoreProperty(propertyName);
                return;
            }
        }
        this.beanInstructions.mapColumnNameToProperty(columnName, propertyName);
        this.beanInstructions.mapColumnIndexToProperty(currentColumn.getColumnIndex(), propertyName);
        currentColumn = currentColumn.nextColumn();
    }

    /**
     * Parses the csv localized number.
     *
     * @param propertyName
     *            the property name
     * @param annotation
     *            the annotation
     */
    private void parseCsvLocalizedNumber(String propertyName, CsvLocalizedNumber annotation) {
        final Locale locale;
        if (annotation.country().isEmpty()) {
            locale = new Locale(annotation.language());
        } else if (annotation.variant().isEmpty()) {
            locale = new Locale(annotation.language(), annotation.country());
        } else {
            locale = new Locale(annotation.language(), annotation.country(), annotation.variant());
        }
        this.beanInstructions.setLocalizedNumber(propertyName, locale);
    }

    /**
     * Parses the csv file.
     *
     * @param csvFile
     *            the csv file
     */
    private void parseCsvFile(CsvFile csvFile) {

        this.beanInstructions.setEscape(csvFile.escape()).setQuote(csvFile.quote())
                .setQuotingEnabled(csvFile.quotingEnabled()).setSeparator(csvFile.separator())
                .setComment(csvFile.comment()).setEndOfLine(csvFile.endOfLine()).setMapper(csvFile.mappingStrategy())
                .setStartRow(csvFile.startRow()).setUseHeader(csvFile.useHeader())
                .skipEmptyLines(csvFile.skipEmptyLines()).skipCommentLines(csvFile.skipCommentLines())
                .setStartIndexDynamicColumns(csvFile.startIndexDynamicColumns());
    }

    /**
     * Parses the csv date.
     *
     * @param propertyName
     *            the property name
     * @param csvDate
     *            the csv date
     */
    private void parseCsvDate(String propertyName, CsvDate csvDate) {
        this.beanInstructions.setDate(propertyName, csvDate.format());
    }

    /**
     * Parses the csv converter.
     *
     * @param propertyName
     *            the property name
     * @param csvConverter
     *            the csv converter
     */
    private void parseCsvConverter(String propertyName, CsvConverter csvConverter) {
        try {
            this.beanInstructions.setConverter(propertyName,
                    csvConverter.converter().getDeclaredConstructor().newInstance());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    /**
     * Parses the csv cell.
     *
     * @param propertyName
     *            the property name
     * @param csvCell
     *            the csv cell
     *
     * @return the string
     */
    private String parseCsvCell(String propertyName, CsvCell csvCell) {
        String columnName = csvCell.columnName() == null || csvCell.columnName().isEmpty() ? propertyName
                : csvCell.columnName();
        this.beanInstructions.setRequired(propertyName, csvCell.required());
        currentColumn = csvCell.columnIndex() != -1 ? new Column(csvCell.columnIndex()) : currentColumn;
        return columnName;
    }

}
