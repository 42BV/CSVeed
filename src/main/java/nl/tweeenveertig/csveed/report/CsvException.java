package nl.tweeenveertig.csveed.report;

import nl.tweeenveertig.csveed.csv.structure.RowReport;

public class CsvException extends RuntimeException {

    private RowReport report;

    private String message;

    public CsvException(String message, RowReport report) {
        this.message = message;
        this.report = report;
    }

    public RowReport getReport() {
        return this.report;
    }

    public String getMessage() {
        return message;
    }
}
