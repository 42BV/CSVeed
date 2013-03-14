package org.csveed.test.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvFile;
import org.csveed.bean.ColumnNameMapper;

@CsvFile(mappingStrategy = ColumnNameMapper.class)
public class BeanWithNameMatching {

    @CsvCell(columnName = "postal code")
    public String line3;

    @CsvCell(columnName = "street")
    public String line1;

    @CsvCell(columnName = "city")
    public String line2;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }
}
