package nl.tweeenveertig.csveed;

import nl.tweeenveertig.csveed.bean.annotations.CsvCell;
import nl.tweeenveertig.csveed.bean.annotations.CsvConverter;
import nl.tweeenveertig.csveed.bean.annotations.CsvDate;
import sun.beans.editors.LongEditor;

import java.util.Date;

public class SomeClass {

    @CsvDate(format="yyyy-MM")
    private Date gamma;

    @CsvCell(name="Alpha Centauri", required=true, indexColumn = 4)
    private Integer alpha;

    @CsvConverter(converter = LongEditor.class)
    private Long beta;

    public Integer getAlpha() {
        return alpha;
    }

    public void setAlpha(Integer alpha) {
        this.alpha = alpha;
    }

    public Long getBeta() {
        return beta;
    }

    public void setBeta(Long beta) {
        this.beta = beta;
    }

    public Date getGamma() {
        return gamma;
    }

    public void setGamma(Date gamma) {
        this.gamma = gamma;
    }
}
