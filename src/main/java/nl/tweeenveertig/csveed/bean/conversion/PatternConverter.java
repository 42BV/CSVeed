package nl.tweeenveertig.csveed.bean.conversion;

import java.util.regex.Pattern;

public class PatternConverter extends AbstractConverter<Pattern> {

    private final int flags;

    public PatternConverter() {
        this.flags = 0;
    }

    public PatternConverter(int flags) {
        this.flags = flags;
    }

    @Override
    public Pattern fromString(String text) throws Exception {
        return text != null ? Pattern.compile(text, this.flags) : null;
    }

    @Override
    public String infoOnType() {
        return getType(Pattern.class);
    }

//    @Override
//    public String toString(Pattern value) throws Exception {
//        return value != null ? value.pattern() : "";
//    }
}
