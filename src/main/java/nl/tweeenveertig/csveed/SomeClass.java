package nl.tweeenveertig.csveed;

import nl.tweeenveertig.csveed.annotations.CsvDate;

import java.util.Date;

public class SomeClass {

    private Integer alpha;

//    @CsvConverter(converter = CustomDateEditor.class)
    private Long beta;

    @CsvDate(format="yyyy-MM")
    private Date gamma;

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
