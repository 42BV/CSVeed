package nl.tweeenveertig.csveed.report;

public class CsvException extends RuntimeException {

    private RowReport report;

    private int lineNumber;

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable err) {
        super(message, err);
    }

    public CsvException(String message, Throwable err, RowReport report, int lineNumber) {
        super(message, err);
        this.report = report;
        this.lineNumber = lineNumber;
    }

    public RowReport getReport() {
        return this.report;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
