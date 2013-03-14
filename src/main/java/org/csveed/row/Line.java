package org.csveed.row;

import org.csveed.report.RowReport;

public interface Line extends Iterable<String> {

    int size();

    String get(int index);

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(int columnIndex);

}
