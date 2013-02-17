package nl.tweeenveertig.csveed.report;

import nl.tweeenveertig.csveed.csv.structure.RowReport;

public class CsvException extends RuntimeException {

    private RowReport report;

    private int lineNumber;

    public CsvException(String message, Throwable err, RowReport report, int lineNumber) {
        super(message, err);
        this.report = report;
    }

    public RowReport getReport() {
        return this.report;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
