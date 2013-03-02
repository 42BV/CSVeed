package nl.tweeenveertig.csveed.report;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCsvError implements CsvError {

    private String message;

    public AbstractCsvError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    protected List<String> getMessageAsList() {
        List<String> lines = new ArrayList<String>();
        lines.add(getMessage());
        return lines;
    }

}
