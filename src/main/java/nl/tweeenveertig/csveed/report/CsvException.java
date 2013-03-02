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

}
