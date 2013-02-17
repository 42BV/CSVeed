package nl.tweeenveertig.csveed.csv.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ParseStateMachineTest {

    @Test
    public void simpleTest() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('"'));
        assertNull(machine.offerSymbol('a'));
        assertNull(machine.offerSymbol('"'));
        assertEquals("a", machine.offerSymbol(-1));
    }

    @Test
    public void emptyColumns() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(-1));
    }

    @Test(expected = ParseException.class)
    public void cellNotFinished() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        feedStateMachine(machine, "\"alpha\";\"beta\";\"ga");
    }

    protected void feedStateMachine(ParseStateMachine machine, String symbols) throws ParseException {
        for (char symbol : symbols.toCharArray()) {
            machine.offerSymbol(symbol);
        }
        machine.offerSymbol(-1);
    }
}
