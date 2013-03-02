package nl.tweeenveertig.csveed.row;

import nl.tweeenveertig.csveed.report.RowReport;

public interface Line extends Iterable<String> {

    int size();

    String get(int index);

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(int columnIndex);

}
