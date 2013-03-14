package org.csveed.test.model;

/**
* Note how gamma, beta and alpha are printed in reverse order. This is done on purpose to test whether the
* declaration order is taken into account
*/
public class BeanWithMultipleStrings {

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
