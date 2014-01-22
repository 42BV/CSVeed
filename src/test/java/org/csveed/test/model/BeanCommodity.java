package org.csveed.test.model;

import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;
import org.csveed.bean.ColumnNameMapper;

@CsvFile(mappingStrategy = ColumnNameMapper.class, startIndexDynamicColumns = 2)
public class BeanCommodity {

    private String commodity;

    private String language;

    // @CsvHeaderName
    @CsvIgnore
    private String day;

    // @CsvHeaderValue
    @CsvIgnore
    private int amount;

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
