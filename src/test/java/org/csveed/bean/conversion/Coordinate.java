/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Coordinate.
 */
public class Coordinate {

    /** The x. */
    private final Integer x;

    /** The y. */
    private final Integer y;

    /**
     * Instantiates a new coordinate.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     */
    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * From string.
     *
     * @param coordinateText
     *            the coordinate text
     *
     * @return the coordinate
     */
    public static Coordinate fromString(String coordinateText) {
        Pattern r = Pattern.compile("(\\d+)/(\\d+)");
        Matcher m = r.matcher(coordinateText);
        if (m.find()) {
            return new Coordinate(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        }
        return null;
    }

    @Override
    public String toString() {
        return x + "/" + y;
    }

}
