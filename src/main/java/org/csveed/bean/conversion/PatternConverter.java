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

import java.util.regex.Pattern;

public class PatternConverter extends AbstractConverter<Pattern> {

    private final int flags;

    public PatternConverter() {
        this(0);
    }

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
