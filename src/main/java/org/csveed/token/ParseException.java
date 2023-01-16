package org.csveed.token;

public class ParseException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ParseState state;

    private final EncounteredSymbol symbol;

    private final int symbolCharacter;

    public ParseException(ParseState state, int symbolCharacter, EncounteredSymbol symbol) {
        this.state = state;
        this.symbolCharacter = symbolCharacter;
        this.symbol = symbol;
    }

    public ParseState getState() {
        return state;
    }

    public EncounteredSymbol getSymbol() {
        return symbol;
    }

    public int getSymbolCharacter() {
        return symbolCharacter;
    }

    @Override
    public String getMessage() {
        return "Illegal state transition: Parsing symbol " + getSymbol() + " [" + getSymbolCharacter() + "] in state "
                + getState();
    }

}
