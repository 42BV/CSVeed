package nl.tweeenveertig.csveed.csv.parser;

public enum TokenState {
    RESET,
    START,
    PROCESSING;

    public TokenState next() {
        return values()[(ordinal() + 1) % 3];
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isReset() {
        return this == RESET;
    }
}
