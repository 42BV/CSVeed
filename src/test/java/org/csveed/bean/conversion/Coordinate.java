package org.csveed.bean.conversion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private final Integer x;

    private final Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public static Coordinate fromString(String coordinateText) {
        Pattern r = Pattern.compile("(\\d+)/(\\d+)");
        Matcher m = r.matcher(coordinateText);
        if (m.find()) {
            return new Coordinate(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2))
            );
        }
        return null;
    }

    @Override
    public String toString() {
        return x + "/" + y;
    }

}
