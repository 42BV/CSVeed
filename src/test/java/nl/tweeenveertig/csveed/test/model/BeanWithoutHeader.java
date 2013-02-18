package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.bean.annotations.CsvDate;
import nl.tweeenveertig.csveed.bean.annotations.CsvFile;

import java.util.Date;

@CsvFile(useHeader = false)
public class BeanWithoutHeader {

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
