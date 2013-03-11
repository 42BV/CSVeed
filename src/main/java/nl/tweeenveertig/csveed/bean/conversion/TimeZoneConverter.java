package nl.tweeenveertig.csveed.bean.conversion;

import java.util.TimeZone;

public class TimeZoneConverter implements Converter<TimeZone> {

    @Override
    public TimeZone fromString(String text) throws Exception {
        return TimeZone.getTimeZone(text);
    }

    @Override
    public String toString(TimeZone value) throws Exception {
        return null;
    }
}
