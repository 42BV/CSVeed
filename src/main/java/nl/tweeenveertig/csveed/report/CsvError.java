package nl.tweeenveertig.csveed.report;

import java.util.List;

public interface CsvError {

    List<String> getPrintableLines();

    List<RowPart> getRowParts();

    int getLineNumber();

    String getMessage();

}
