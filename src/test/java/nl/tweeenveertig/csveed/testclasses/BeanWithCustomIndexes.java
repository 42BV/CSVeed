package nl.tweeenveertig.csveed.testclasses;

import nl.tweeenveertig.csveed.bean.annotations.CsvCell;
import nl.tweeenveertig.csveed.bean.annotations.CsvFile;

@CsvFile(useHeader = false)
public class BeanWithCustomIndexes {

    @CsvCell(indexColumn = 4)
    private String line3;

    @CsvCell(indexColumn = 1)
    private String line0;

    @CsvCell(indexColumn = 2)
    private String line1;

    @CsvCell(indexColumn = 3)
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
