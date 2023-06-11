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
package org.csveed.test.model;

import java.util.Date;

import org.csveed.annotations.CsvDate;

/**
 * The Class BeanWithVariousTypes.
 */
public class BeanWithVariousTypes {

    /** The text. */
    private String text;

    /** The year. */
    private Integer year;

    /** The number. */
    private Double number;

    /** The date. */
    @CsvDate(format = "yyyy-MM-dd")
    private Date date;

    /** The year month. */
    @CsvDate(format = "yyyy-MM")
    private Date yearMonth;

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text
     *            the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the year.
     *
     * @param year
     *            the new year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    public Double getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number
     *            the new number
     */
    public void setNumber(Double number) {
        this.number = number;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date
     *            the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the year month.
     *
     * @return the year month
     */
    public Date getYearMonth() {
        return yearMonth;
    }

    /**
     * Sets the year month.
     *
     * @param yearMonth
     *            the new year month
     */
    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }
}
