package nl.tweeenveertig.csveed.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvException extends RuntimeException {

    public static final Logger LOG = LoggerFactory.getLogger(CsvException.class);

    private CsvError error;

    public CsvException(CsvError error) {
        this.error = error;
        for (String line : error.getPrintableLines()) {
            LOG.error(line);
        }
    }

    public CsvError getError() {
        return this.error;
    }

//    private RowReport report;
//
//    private int lineNumber;
//
//    public CsvException(String message) {
//        super(message);
//    }
//
//    public CsvException(String message, Throwable err) {
//        super(message, err);
//    }
//
//    public CsvException(String message, Throwable err, RowReport report, int lineNumber) {
//        super(message, err);
//        this.report = report;
//        this.lineNumber = lineNumber;
//    }
//
//    public RowReport getReport() {
//        return this.report;
//    }
//
//    public int getLineNumber() {
//        return lineNumber;
//    }

}
