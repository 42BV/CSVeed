package nl.tweeenveertig.csveed.csv.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TokenStateTest {

    @Test
    public void nextState() {
        assertEquals(TokenState.RESET, TokenState.PROCESSING.next());
        assertEquals(TokenState.START, TokenState.RESET.next());
        assertEquals(TokenState.PROCESSING, TokenState.START.next());
    }
}
