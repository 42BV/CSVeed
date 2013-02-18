package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.bean.annotations.CsvCell;
import nl.tweeenveertig.csveed.bean.annotations.CsvFile;
import nl.tweeenveertig.csveed.bean.annotations.MappingStrategy;

@CsvFile(mappingStrategy = MappingStrategy.NAME_MATCHING)
public class BeanWithNameMatching {

    @CsvCell(name = "postal code")
    public String line3;

    @CsvCell(name = "street")
    public String line1;

    @CsvCell(name = "city")
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
