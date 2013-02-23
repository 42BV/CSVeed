package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.line.Header;
import nl.tweeenveertig.csveed.report.RowReport;

public interface Row extends Iterable<String> {

    int size();

    String get(int index);

    String get(String columnName);

    String getColumnName(int index);

    boolean hasHeader();

    Header getHeader();

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(int columnIndex);

    boolean validNumberOfColumns();

}
