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

//    @Override
//    public String toString(TimeZone value) throws Exception {
//        return null;
//    }
}
