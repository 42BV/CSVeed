package nl.tweeenveertig.csveed.report;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

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
