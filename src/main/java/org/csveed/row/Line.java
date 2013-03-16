package org.csveed.row;

import org.csveed.common.Column;
import org.csveed.report.RowReport;

public interface Line extends Iterable<String> {

    int size();

    String get(int index);

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(Column column);

}
