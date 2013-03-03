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

    @Override
    public String getMessage() {
        return this.getError().getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        StringBuilder errorMessage = new StringBuilder();
        boolean first = true;
        for (String line : getError().getPrintableLines()) {
            if (!first) {
                errorMessage.append(System.getProperty("line.separator"));
            }
            errorMessage.append(line);
            first = false;
        }
        return errorMessage.toString();
    }

}
