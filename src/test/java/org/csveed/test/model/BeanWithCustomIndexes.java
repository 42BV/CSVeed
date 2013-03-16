package org.csveed.test.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvFile;

@CsvFile(useHeader = false)
public class BeanWithCustomIndexes {

    @CsvCell(columnIndex = 5)
    private String line3;

    @CsvCell(columnIndex = 2)
    private String line0;

    @CsvCell(columnIndex = 3)
    private String line1;

    @CsvCell(columnIndex = 4)
    private String line2;

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine0() {
        return line0;
    }

    public void setLine0(String line0) {
        this.line0 = line0;
    }

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
}
