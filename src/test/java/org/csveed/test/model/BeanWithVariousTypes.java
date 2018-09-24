package org.csveed.test.model;

import java.util.Date;

import org.csveed.annotations.CsvDate;

public class BeanWithVariousTypes {

    private String text;

    private Integer year;

    private Double number;

    @CsvDate(format = "yyyy-MM-dd")
    private Date date;

    @CsvDate(format = "yyyy-MM")
    private Date yearMonth;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
