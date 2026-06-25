/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import java.util.TimeZone;

/**
 * The Class TimeZoneConverter.
 */
public class TimeZoneConverter extends AbstractConverter<TimeZone> {

    /**
     * Instantiates a new time zone converter.
     */
    public TimeZoneConverter() {
        super(TimeZone.class);
    }

    @Override
    public TimeZone fromString(String text) throws Exception {
        return TimeZone.getTimeZone(text);
    }

    @Override
    public String toString(TimeZone value) throws Exception {
        return value.getDisplayName();
    }
}
