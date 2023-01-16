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

import java.util.TimeZone;

public class TimeZoneConverter extends AbstractConverter<TimeZone> {

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
