/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.model;

import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvHeaderName;
import org.csveed.annotations.CsvHeaderValue;
import org.csveed.bean.ColumnNameMapper;

/**
 * The Class BeanCommodity.
 */
@CsvFile(mappingStrategy = ColumnNameMapper.class, startIndexDynamicColumns = 3)
public class BeanCommodity {

    /** The commodity. */
    private String commodity;

    /** The language. */
    private String language;

    /** The day. */
    @CsvHeaderName
    private String day;

    /** The amount. */
    @CsvHeaderValue
    private int amount;

    /**
     * Gets the commodity.
     *
     * @return the commodity
     */
    public String getCommodity() {
        return commodity;
    }

    /**
     * Sets the commodity.
     *
     * @param commodity
     *            the new commodity
     */
    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     *
     * @param language
     *            the new language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the day.
     *
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the day.
     *
     * @param day
     *            the new day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *            the new amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
