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
package org.csveed.row;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowReport;
import org.junit.jupiter.api.Test;

/**
 * The Class LineWithInfoTest.
 */
class LineWithInfoTest {

    /**
     * Cell is null.
     */
    @Test
    void cellIsNull() {
        LineWithInfo row = new LineWithInfo();
        row.addCell(null);
        assertEquals(0, row.getCellPosition(new Column()).getStart());
        assertEquals(0, row.getCellPosition(new Column()).getEnd());
    }

    /**
     * Cell is empty.
     */
    @Test
    void cellIsEmpty() {
        LineWithInfo row = new LineWithInfo();
        row.addCell("");
        assertEquals(0, row.getCellPosition(new Column()).getStart());
        assertEquals(0, row.getCellPosition(new Column()).getEnd());
    }

    /**
     * Convert characters.
     */
    @Test
    void convertCharacters() {
        LineWithInfo row = new LineWithInfo();
        assertEquals("\\b", row.convertToPrintable('\b'));
        assertEquals("\\f", row.convertToPrintable('\f'));
    }

    /**
     * Non existing cell.
     */
    @Test
    void nonExistingCell() {
        LineWithInfo row = new LineWithInfo();
        assertNull(row.reportOnColumn(new Column()));
    }

    /**
     * Gets the report on column index 0.
     */
    @Test
    void getReportOnColumnIndex0() {
        LineWithInfo row = addString(new LineWithInfo(), "Hello");
        assertThrows(CsvException.class, () -> {
            row.reportOnColumn(new Column(0));
        });
    }

    /**
     * Simple word.
     */
    @Test
    void simpleWord() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Hello");
        RowReport report = row.reportOnColumn(new Column());
        assertEquals("Hello", report.getRow());
        assertEquals(0, report.getStart());
        assertEquals(5, report.getEnd());
    }

    /**
     * Couple of words.
     */
    @Test
    void coupleOfWords() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter(';');
        row = addString(row, "Beta");
        row.addCharacter(';');
        row = addString(row, "Gamma");
        RowReport report = row.reportOnColumn(new Column(3));
        assertEquals("Alpha;Beta;Gamma", report.getRow());
        assertEquals(11, report.getStart());
        assertEquals(16, report.getEnd());
    }

    /**
     * Various non printables.
     */
    @Test
    void variousNonPrintables() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter('\t');
        row = addString(row, "Beta");
        row.addCharacter('\t');
        row = addString(row, "Gamma");
        RowReport report = row.reportOnColumn(new Column(3));
        assertEquals("Alpha\\tBeta\\tGamma", report.getRow());
        assertEquals(13, report.getStart());
        assertEquals(18, report.getEnd());
    }

    /**
     * Report on end of line.
     */
    @Test
    void reportOnEndOfLine() {
        LineWithInfo row = new LineWithInfo();
        row = addString(row, "Alpha");
        row.addCharacter('\t');
        row = addString(row, "Beta");
        row.addCharacter('@');
        RowReport report = row.reportOnEndOfLine();
        assertEquals("Alpha\\tBeta@", report.getRow());
        assertEquals(12, report.getStart());
        assertEquals(12, report.getEnd());
    }

    /**
     * Adds the string.
     *
     * @param row
     *            the row
     * @param text
     *            the text
     *
     * @return the line with info
     */
    protected LineWithInfo addString(LineWithInfo row, String text) {
        row.markStartOfColumn();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            row.addCharacter(character);
        }
        row.addCell(text);
        return row;
    }

}
