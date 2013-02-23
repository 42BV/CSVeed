package nl.tweeenveertig.csveed.report;

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

        StringBuilder line = new StringBuilder();
        for (RowPart token : parts) {
            line.append(token.getToken());
        }
        lines.add(line.toString());

        line = new StringBuilder();
        boolean placedMarkers = false;
        for (RowPart token : parts) {
            if (token.isHighlight()) {
                for (int i = 0; i < token.getToken().length(); i++) {
                    if (i == 0 || i == token.getToken().length() - 1) {
                        line.append('^');
                        placedMarkers = true;
                    } else {
                        line.append('-');
                    }
                }
            } else {
                for (int i = 0; i < token.getToken().length(); i++) {
                    line.append(' ');
                }
            }
        }
        if (!placedMarkers) { // Essentially only at the end-of-line
            line.append('^');
        }
        lines.add(line.toString());

        return lines;
    }
}
