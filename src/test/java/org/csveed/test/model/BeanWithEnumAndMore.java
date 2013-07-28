package org.csveed.test.model;

import org.csveed.token.ParseState;

public class BeanWithEnumAndMore {

    private String name;

    private ParseState parseState;

    public ParseState getParseState() {
        return parseState;
    }

    public void setParseState(ParseState parseState) {
        this.parseState = parseState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
