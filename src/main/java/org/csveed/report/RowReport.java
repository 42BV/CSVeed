package org.csveed.report;

import java.util.ArrayList;
import java.util.List;

public class RowReport {

    private String row;

    private int start;

    private int end;

    public RowReport(String row, int start, int end) {
        this.row = row;
        this.start = start;
        this.end = start == end && start < row.length() ? start + 1 : end;
    }

    public String getRow() {
        return row;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public List<RowPart> tokenize() {
        List<RowPart> lines = new ArrayList<RowPart>();
        if (start > 0) {
            lines.add(new RowPart(row.substring(0, start), false));
        }
        if (end - start > 0) {
            lines.add(new RowPart(row.substring(start, end), true));
        }
        if (end < row.length()) {
            lines.add(new RowPart(row.substring(end), false));
        }
        return lines;
    }

    public List<String> getPrintableLines() {
        List<String> lines = new ArrayList<String>();

        List<RowPart> parts = tokenize();

        lines.add(createContentLine(parts));
        lines.add(createFocusLine(parts));

        return lines;
    }

    private String createContentLine(List<RowPart> parts) {
        StringBuilder line = new StringBuilder();
        for (RowPart token : parts) {
            line.append(token.getToken());
        }
        return line.toString();
    }

    private String createFocusLine(List<RowPart> parts) {
        StringBuilder line = new StringBuilder();
        boolean placedMarkers = false;
        for (RowPart token : parts) {
            if (token.isHighlight()) {
                line.append(printUnderscoredPart(token));
                placedMarkers = true;
            } else {
                line.append(printEmptyPart(token));
            }
        }
        if (!placedMarkers) { // Essentially only at the end-of-line
            line.append('^');
        }
        return line.toString();
    }

    private String printEmptyPart(RowPart token) {
        StringBuilder linePart = new StringBuilder();
        for (int i = 0; i < token.getToken().length(); i++) {
            linePart.append(' ');
        }
        return linePart.toString();
    }

    private String printUnderscoredPart(RowPart token) {
        StringBuilder linePart = new StringBuilder();
        for (int i = 0; i < token.getToken().length(); i++) {
            if (i == 0 || i == token.getToken().length() - 1) {
                linePart.append('^');
            } else {
                linePart.append('-');
            }
        }
        return linePart.toString();
    }

}
