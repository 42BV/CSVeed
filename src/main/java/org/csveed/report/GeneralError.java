package org.csveed.report;

import java.util.ArrayList;
import java.util.List;

public class GeneralError extends AbstractCsvError {

    public GeneralError(String message) {
        super(message);
    }

    @Override
    public List<String> getPrintableLines() {
        return getMessageAsList();
    }

    @Override
    public List<RowPart> getRowParts() {
        return new ArrayList<>();
    }

    @Override
    public int getLineNumber() {
        return -1;
    }
}
