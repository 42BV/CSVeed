/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The Class ParseStateMachineTest.
 */
public class ParseStateMachineTest {

    /**
     * Columns.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void columns() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertEquals(1, machine.getCurrentColumn());
        machine.offerSymbol(';');
        assertEquals(2, machine.getCurrentColumn());
        machine.offerSymbol(';');
        assertEquals(3, machine.getCurrentColumn());
    }

    /**
     * Windows cr lf.
     *
     * @throws ParseException
     *             the parse exception
     */
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

    /**
     * Comment line.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void commentLine() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('-'));
        assertNull(machine.offerSymbol('#'));
        assertNull(machine.offerSymbol('\n'));
        assertEquals("", machine.offerSymbol(';'));
    }

    /**
     * Simple test.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void simpleTest() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol('"'));
        assertNull(machine.offerSymbol('a'));
        assertNull(machine.offerSymbol('"'));
        assertEquals("a", machine.offerSymbol(-1));
    }

    /**
     * Empty columns.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void emptyColumns() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(';'));
        assertEquals("", machine.offerSymbol(-1));
    }

    /**
     * Illegal state.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void illegalState() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(-1);
        assertThrows(ParseException.class, () -> {
            machine.offerSymbol(-1);
        });
    }

    /**
     * Illegal characters after quoted content.
     */
    @Test
    public void illegalCharactersAfterQuotedContent() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "    \"alpha\"  ; \"beta\"   x; \"gamma\" ");
        });
    }

    /**
     * Before field with EOL.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void beforeFieldWithEOL() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(' ');
        machine.offerSymbol('\n');
        assertTrue(machine.isLineFinished());
    }

    /**
     * Before field with EOF.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void beforeFieldWithEOF() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        machine.offerSymbol(' ');
        machine.offerSymbol(-1);
        assertTrue(machine.isLineFinished());
        assertTrue(machine.isFinished());
    }

    /**
     * Before field with separator.
     *
     * @throws ParseException
     *             the parse exception
     */
    @Test
    public void beforeFieldWithSeparator() throws ParseException {
        ParseStateMachine machine = new ParseStateMachine();
        assertNull(machine.offerSymbol(' '));
        assertEquals("", machine.offerSymbol(';'));
    }

    /**
     * Cell not finished.
     */
    @Test
    public void cellNotFinished() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "\"alpha\";\"beta\";\"ga");
        });
    }

    /**
     * Double quotes after field info started.
     */
    @Test
    public void doubleQuotesAfterFieldInfoStarted() {
        ParseStateMachine machine = new ParseStateMachine();
        assertThrows(ParseException.class, () -> {
            feedStateMachine(machine, "some text and... \"double quote\"... WAT?;\"beta\";\"ga\"");
        });
    }

    /**
     * Feed state machine.
     *
     * @param machine
     *            the machine
     * @param symbols
     *            the symbols
     *
     * @throws ParseException
     *             the parse exception
     */
    protected void feedStateMachine(ParseStateMachine machine, String symbols) throws ParseException {
        for (int i = 0; i < symbols.length(); i++) {
            char symbol = symbols.charAt(i);
            machine.offerSymbol(symbol);
        }
        machine.offerSymbol(-1);
    }
}
