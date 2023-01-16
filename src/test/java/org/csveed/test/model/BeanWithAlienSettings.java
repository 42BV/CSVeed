package org.csveed.test.model;

import org.csveed.annotations.CsvFile;

@CsvFile(escape = '\\', endOfLine = '\r', separator = '\t', quote = '\'')
public class BeanWithAlienSettings {

    private String gamma;

    private String beta;

    private String alpha;

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }
}
