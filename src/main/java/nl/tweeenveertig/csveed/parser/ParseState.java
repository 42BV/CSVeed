package nl.tweeenveertig.csveed.parser;

public enum ParseState {
    OUTSIDE_FIELD                  (false, false, false, false),
    INSIDE_FIELD                   (true,  false, false, false),
    FIRST_CHAR_INSIDE_QUOTED_FIELD (false, false, false, true),
    INSIDE_QUOTED_FIELD            (true,  false, false, true),
    ESCAPING                       (false, false, false, false),
    SEPARATOR                      (false, false, true,  false),
    LINE_FINISHED                  (false, true,  true,  false),
    FINISHED                       (false, true,  true,  false);

    /**
    * When in this state, encountered symbols must be made part of the token
    */
    private boolean tokenize;

    /**
    * When in this state, reading of the line is done
    */
    private boolean lineFinished;

    /**
    * When in this state, the token may be popped
    */
    private boolean popToken;

    /**
    * When a quote character is encountered here, this state MAY trigger an upgrade of the encountered symbol
    */
    private boolean upgradeQuoteToEscape;

    private ParseState(final boolean tokenize, final boolean lineFinished, final boolean popToken,
                       final boolean upgradeQuoteToEscape) {
        this.tokenize = tokenize;
        this.lineFinished = lineFinished;
        this.popToken = popToken;
        this.upgradeQuoteToEscape = upgradeQuoteToEscape;
    }

    public boolean isTokenize() {
        return this.tokenize;
    }

    public boolean isLineFinished() {
        return this.lineFinished;
    }

    public boolean isPopToken() {
        return this.popToken;
    }

    public boolean isUpgradeQuoteToEscape() {
        return this.upgradeQuoteToEscape;
    }
}
