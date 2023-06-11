/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.test.model;

import org.csveed.annotations.CsvFile;

/**
 * The Class BeanWithAlienSettings.
 */
@CsvFile(escape = '\\', endOfLine = '\r', separator = '\t', quote = '\'')
public class BeanWithAlienSettings {

    /** The gamma. */
    private String gamma;

    /** The beta. */
    private String beta;

    /** The alpha. */
    private String alpha;

    /**
     * Gets the alpha.
     *
     * @return the alpha
     */
    public String getAlpha() {
        return alpha;
    }

    /**
     * Sets the alpha.
     *
     * @param alpha
     *            the new alpha
     */
    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    /**
     * Gets the beta.
     *
     * @return the beta
     */
    public String getBeta() {
        return beta;
    }

    /**
     * Sets the beta.
     *
     * @param beta
     *            the new beta
     */
    public void setBeta(String beta) {
        this.beta = beta;
    }

    /**
     * Gets the gamma.
     *
     * @return the gamma
     */
    public String getGamma() {
        return gamma;
    }

    /**
     * Sets the gamma.
     *
     * @param gamma
     *            the new gamma
     */
    public void setGamma(String gamma) {
        this.gamma = gamma;
    }
}
