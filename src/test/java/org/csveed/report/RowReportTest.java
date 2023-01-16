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
package org.csveed.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RowReportTest {

    @Test
    public void relevantBlockAtStart() {
        RowReport report = new RowReport("0123456789", 0, 4);
        assertEquals(2, report.tokenize().size());
        assertEquals("0123", report.tokenize().get(0).getToken());
        assertEquals("456789", report.tokenize().get(1).getToken());
    }

    @Test
    public void relevantBlockInMiddle() {
        RowReport report = new RowReport("0123456789", 3, 6);
        assertEquals(3, report.tokenize().size());
        assertEquals("012", report.tokenize().get(0).getToken());
        assertEquals("345", report.tokenize().get(1).getToken());
        assertEquals("6789", report.tokenize().get(2).getToken());
    }

    @Test
    public void relevantBlockAtEnd() {
        RowReport report = new RowReport("0123456789", 7, 10);
        assertEquals(2, report.tokenize().size());
        assertEquals("0123456", report.tokenize().get(0).getToken());
        assertEquals("789", report.tokenize().get(1).getToken());
    }

    @Test
    public void tooSmallToNotice() {
        RowReport report = new RowReport("0123456789", 7, 7);
        assertEquals(3, report.tokenize().size());
        assertEquals("0123456", report.tokenize().get(0).getToken());
        assertEquals("7", report.tokenize().get(1).getToken());
        assertEquals("89", report.tokenize().get(2).getToken());
    }

    @Test
    public void onlyAtTheEnd() {
        RowReport report = new RowReport("0123456789", 10, 10);
        List<String> lines = report.getPrintableLines();
        assertEquals("          ^", lines.get(1));
    }

}
