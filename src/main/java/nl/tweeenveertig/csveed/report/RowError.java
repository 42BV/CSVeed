package nl.tweeenveertig.csveed.report;

import java.util.List;

public class RowError extends AbstractCsvError {

    private RowReport report;

    private int lineNumber;

    public RowError(String message, RowReport report, int lineNumber) {
        super(message);
        this.report = report;
        this.lineNumber = lineNumber;
    }

    public RowReport getReport() {
        return report;
    }

    @Override
    public List<String> getPrintableLines() {
        List<String> lines = getMessageAsList();
        List<String> lineReport = report.getPrintableLines();
        lines.add(lineNumber + ": " + lineReport.get(0));
        lines.add(lineNumber + ": " + lineReport.get(1));
        return lines;
    }

    @Override
    public List<RowPart> getRowParts() {
        return report.tokenize();
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

}
