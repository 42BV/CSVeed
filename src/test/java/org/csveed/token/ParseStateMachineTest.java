package org.csveed.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParseStateMachineTest {

    @Test
    public void columns() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertEquals(1, machine.getCurrentColumn());
        machine.offerSymbol(';');
        assertEquals(2, machine.getCurrentColumn());
        machine.offerSymbol(';');
        assertEquals(3, machine.getCurrentColumn());
    }

    @Test
    public void windowsCrLf() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(';');
        machine.offerSymbol(0x0d);
        machine.offerSymbol(0x0a);
        machine.offerSymbol(';');
        assertEquals(2, machine.getCurrentLine());
        machine.offerSymbol(0x0d);
        machine.offerSymbol(0x0a);
        machine.offerSymbol(-1);
        assertEquals(3, machine.getCurrentLine());
    }

    @Test
    public void commentLine() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('-'));
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('\n'));
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

    @Test
    public void illegalState() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(-1);
        assertThrows(ParseException.class, () -> {
            machine.offerSymbol(-1);
        });
    }

    @Test
    public void illegalCharactersAfterQuotedContent() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "    \"alpha\"  ; \"beta\"   x; \"gamma\" ");
        });
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

    @Test
    public void cellNotFinished() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "\"alpha\";\"beta\";\"ga");
        });
    }

    @Test
    public void doubleQuotesAfterFieldInfoStarted() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "some text and... \"double quote\"... WAT?;\"beta\";\"ga\"");
        });
    }

    protected void feedStateMachine(ParseStateMachine machine, String symbols) throws ParseException {
        for (char symbol : symbols.toCharArray()) {
            machine.offerSymbol(symbol);
        }
        machine.offerSymbol(-1);
    }
}
