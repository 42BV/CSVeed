/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.model;

/**
 * Note how gamma, beta and alpha are printed in reverse order. This is done on purpose to test whether the declaration
 * order is taken into account
 */
public class BeanWithMultipleStrings {

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
