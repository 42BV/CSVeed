/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean.conversion;

import java.util.regex.Pattern;

/**
 * The Class PatternConverter.
 */
public class PatternConverter extends AbstractConverter<Pattern> {

    /** The flags. */
    private final int flags;

    /**
     * Instantiates a new pattern converter.
     */
    public PatternConverter() {
        this(0);
    }

    /**
     * Instantiates a new pattern converter.
     *
     * @param flags
     *            the flags
     */
    public PatternConverter(int flags) {
        super(Pattern.class);
        this.flags = flags;
    }

    @Override
    public Pattern fromString(String text) throws Exception {
        return text != null ? Pattern.compile(text, this.flags) : null;
    }

    @Override
    public String toString(Pattern value) throws Exception {
        return value != null ? value.pattern() : "";
    }
}
