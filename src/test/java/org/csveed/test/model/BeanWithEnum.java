package org.csveed.test.model;

import org.csveed.token.ParseState;

public class BeanWithEnum {

    private ParseState parseState;

    public ParseState getParseState() {
        return parseState;
    }

    public void setParseState(ParseState parseState) {
        this.parseState = parseState;
    }
}
