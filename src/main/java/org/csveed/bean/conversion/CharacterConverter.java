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

import static org.csveed.bean.conversion.ConversionUtil.hasLength;

public class CharacterConverter extends AbstractConverter<Character> {

    private static final String UNICODE_PREFIX = "\\u";

    private static final int UNICODE_LENGTH = 6;

    private final boolean allowEmpty;

    public CharacterConverter(boolean allowEmpty) {
        super(Character.class);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Character fromString(String text) throws Exception {
        if (this.allowEmpty && !hasLength(text)) {
            return null;
        }
        if (text == null) {
            throw new IllegalArgumentException("null String cannot be converted to char type");
        }
        if (isUnicodeCharacterSequence(text)) {
            int code = Integer.parseInt(text.substring(UNICODE_PREFIX.length()), 16);
            return (char) code;
        }
        if (text.length() != 1) {
            throw new IllegalArgumentException(
                    "String [" + text + "] with length " + text.length() + " cannot be converted to char type");
        }
        return text.charAt(0);
    }

    @Override
    public String toString(Character value) throws Exception {
        return value != null ? value.toString() : "";
    }

    private boolean isUnicodeCharacterSequence(String sequence) {
        return sequence.startsWith(UNICODE_PREFIX) && sequence.length() == UNICODE_LENGTH;
    }

}
