package nl.tweeenveertig.csveed.token;

import org.junit.Test;

import static junit.framework.Assert.*;

public class ParseStateMachineTest {

    @Test
    public void commentLine() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('-'));
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('\n'));
        machine.newLine();
        assertEquals("", machine.offerSymbol(';'));
    }

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
    public void illegalState() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(-1);
        machine.offerSymbol(-1);
    }

    @Test(expected = ParseException.class)
    public void illegalCharactersAfterQuotedContent() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        feedStateMachine(machine, "    \"alpha\"  ; \"beta\"   x; \"gamma\" ");
    }

    @Test
    public void beforeFieldWithEOL() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(' ');
        machine.offerSymbol('\n');
        assertTrue(machine.isLineFinished());
    }

    @Test
    public void beforeFieldWithEOF() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(' ');
        machine.offerSymbol(-1);
        assertTrue(machine.isLineFinished());
        assertTrue(machine.isFinished());
    }

    @Test
    public void beforeFieldWithSeparator() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol(' '));
        assertEquals("", machine.offerSymbol(';'));
    }

    @Test(expected = ParseException.class)
    public void cellNotFinished() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        feedStateMachine(machine, "\"alpha\";\"beta\";\"ga");
    }

    @Test(expected = ParseException.class)
    public void doubleQuotesAfterFieldInfoStarted() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        feedStateMachine(machine, "some text and... \"double quote\"... WAT?;\"beta\";\"ga\"");
    }

    protected void feedStateMachine(ParseStateMachine machine, String symbols) throws ParseException {
        for (char symbol : symbols.toCharArray()) {
            machine.offerSymbol(symbol);
        }
        machine.offerSymbol(-1);
    }
}
