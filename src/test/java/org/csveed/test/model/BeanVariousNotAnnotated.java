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

/**
 * The Class BeanVariousNotAnnotated.
 */
public class BeanVariousNotAnnotated {

    /** The ignore me. */
    private String ignoreMe;

    /** The txt. */
    private String txt;

    /** The year. */
    private Integer year;

    /** The number. */
    private Double number;

    /** The date. */
    private Date date;

    /** The year month. */
    private Date yearMonth;

    /** The simple. */
    private BeanSimple simple;

    /**
     * Gets the txt.
     *
     * @return the txt
     */
    public String getTxt() {
        return txt;
    }

    /**
     * Sets the txt.
     *
     * @param txt
     *            the new txt
     */
    public void setTxt(String txt) {
        this.txt = txt;
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

    /**
     * Gets the ignore me.
     *
     * @return the ignore me
     */
    public String getIgnoreMe() {
        return ignoreMe;
    }

    /**
     * Sets the ignore me.
     *
     * @param ignoreMe
     *            the new ignore me
     */
    public void setIgnoreMe(String ignoreMe) {
        this.ignoreMe = ignoreMe;
    }

    /**
     * Gets the simple.
     *
     * @return the simple
     */
    public BeanSimple getSimple() {
        return simple;
    }

    /**
     * Sets the simple.
     *
     * @param simple
     *            the new simple
     */
    public void setSimple(BeanSimple simple) {
        this.simple = simple;
    }
}
