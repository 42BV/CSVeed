package nl.tweeenveertig.csveed.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class ParseStateMachineTest {

    @Test
    public void simpleTest() {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('"'));
        assertNull(machine.offerSymbol('a'));
        assertNull(machine.offerSymbol('"'));
        assertEquals("a", machine.offerSymbol(-1));
    }

    @Test
    public void emptyColumns() {
        ParseStateMachine machine = new ParseStateMachine();
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(-1));
    }

}
