package nl.tweeenveertig.csveed.parser;

import static nl.tweeenveertig.csveed.parser.ParseState.*;
import static nl.tweeenveertig.csveed.parser.EncounteredSymbol.*;

/**
* Yep, a state machine. Managing all kinds of booleans to form a pseudo-state doesn't work really well
* whereas a state machine does. The state machine takes one character at a time, checks routes to the new
* state if necessary and holds tokens, which it returns whenever a field-end ('popToken') has been found.
* @author Robert Bor
*/
public class ParseStateMachine {

    private ParseState state = OUTSIDE_FIELD;

    private StringBuilder token = new StringBuilder();

    private int charactersRead = 0;

    private SymbolMapping symbolMapping = new SymbolMapping();

    public String offerSymbol(int symbolCharacter) {
        EncounteredSymbol symbol = symbolMapping.find(symbolCharacter, state);
        ParseState newState = determineState(symbol);
//        System.out.println((char)symbolCharacter+" / "+symbol+": "+state+" => "+newState);
        if (newState.isTokenize()) {
            token.append((char)symbolCharacter);
        }
        String returnToken = null;
        if (newState.isPopToken()) {
            returnToken = token.toString();
            token = new StringBuilder();
        }
        if (!newState.isLineFinished()) {
            charactersRead++;
        }
        state = newState;
        return returnToken;
    }

    public boolean isLineFinished() {
        return state.isLineFinished();
    }

    public boolean isFinished() {
        return state == FINISHED;
    }

    public boolean isEmptyLine() {
        return charactersRead == 0;
    }

    public void newLine() {
        if (state != FINISHED) {
            state = OUTSIDE_FIELD;
        }
        charactersRead = 0;
    }

    protected ParseState determineState(EncounteredSymbol symbol) {
        if (symbol == END_OF_FILE_SYMBOL) {
            return FINISHED;
        }

        switch (state) {
            case SEPARATOR:
            case OUTSIDE_FIELD:
                switch(symbol) {
                    case QUOTE_SYMBOL:
                        return FIRST_CHAR_INSIDE_QUOTED_FIELD;
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    case EOL_SYMBOL :
                        return LINE_FINISHED;
                    default :
                        return INSIDE_FIELD;
                }
            case INSIDE_FIELD:
                switch (symbol) {
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    case EOL_SYMBOL :
                        return LINE_FINISHED;
                    default :
                        return INSIDE_FIELD;
                }
            case FIRST_CHAR_INSIDE_QUOTED_FIELD:
            case INSIDE_QUOTED_FIELD:
                switch (symbol) {
                    case QUOTE_SYMBOL :
                        return OUTSIDE_FIELD;
                    case ESCAPE_SYMBOL :
                        return ESCAPING;
                    default :
                        return INSIDE_QUOTED_FIELD;
                }
            case ESCAPING:
                switch (symbol) {
                    case QUOTE_SYMBOL :
                        return INSIDE_QUOTED_FIELD;
                    case EOL_SYMBOL:
                        return LINE_FINISHED;
                    case SEPARATOR_SYMBOL :
                        return SEPARATOR;
                    default :
                        throw new RuntimeException("Cannot have "+symbol+" at this position");
                }
            default :
                throw new RuntimeException("Illegal state");
        }
    }

    public void setSymbolMapping(SymbolMapping symbolMapping) {
        this.symbolMapping = symbolMapping;
    }

}
