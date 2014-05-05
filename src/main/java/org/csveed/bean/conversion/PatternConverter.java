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

//    @Override
//    public String toString(Pattern value) throws Exception {
//        return value != null ? value.pattern() : "";
//    }
}
