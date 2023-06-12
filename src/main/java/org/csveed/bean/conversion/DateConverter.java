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
package org.csveed.bean.conversion;

import static org.csveed.bean.conversion.ConversionUtil.hasText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateConverter.
 */
public class DateConverter extends AbstractConverter<Date> {

    /** The date format. */
    private final DateFormat dateFormat;

    /** The allow empty. */
    private final boolean allowEmpty;

    /** The exact date length. */
    private final int exactDateLength;

    /** The format text. */
    private final String formatText;

    /**
     * Instantiates a new date converter.
     *
     * @param formatText
     *            the format text
     * @param allowEmpty
     *            the allow empty
     */
    public DateConverter(String formatText, boolean allowEmpty) {
        super(Date.class);
        this.formatText = formatText;
        this.dateFormat = new SimpleDateFormat(formatText);
        this.dateFormat.setLenient(false);
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }

    @Override
    public Date fromString(String text) throws Exception {
        if (this.allowEmpty && !hasText(text)) {
            return null;
        }
        if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            throw new IllegalArgumentException(
                    "Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
        }
        try {
            return this.dateFormat.parse(text);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String infoOnType() {
        return super.infoOnType() + " " + formatText;
    }

    @Override
    public String toString(Date value) {
        return value != null ? this.dateFormat.format(value) : "";
    }

}
