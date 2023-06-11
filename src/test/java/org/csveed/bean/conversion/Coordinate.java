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
