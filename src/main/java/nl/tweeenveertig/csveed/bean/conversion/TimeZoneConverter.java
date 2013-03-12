package nl.tweeenveertig.csveed.bean.conversion;

import java.util.TimeZone;

public class TimeZoneConverter extends AbstractConverter<TimeZone> {

    @Override
    public TimeZone fromString(String text) throws Exception {
        return TimeZone.getTimeZone(text);
    }

    @Override
    public String infoOnType() {
        return getType(TimeZone.class);
    }

//    @Override
//    public String toString(TimeZone value) throws Exception {
//        return null;
//    }
}
