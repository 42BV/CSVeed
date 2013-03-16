package org.csveed.row;

import org.csveed.report.RowReport;
import org.csveed.util.ExcelColumn;

public interface Line extends Iterable<String> {

    int size();

    String get(int index);

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(ExcelColumn column);

}
