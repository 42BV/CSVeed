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

public class BeanVariousNotAnnotated {

    private String ignoreMe;

    private String txt;

    private Integer year;

    private Double number;

    private Date date;

    private Date yearMonth;

    private BeanSimple simple;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getIgnoreMe() {
        return ignoreMe;
    }

    public void setIgnoreMe(String ignoreMe) {
        this.ignoreMe = ignoreMe;
    }

    public BeanSimple getSimple() {
        return simple;
    }

    public void setSimple(BeanSimple simple) {
        this.simple = simple;
    }
}
