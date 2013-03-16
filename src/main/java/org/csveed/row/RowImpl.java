package org.csveed.row;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.report.RowReport;

import java.util.Iterator;

public class RowImpl implements Row {

    private Line line;

    private Header header;

    public RowImpl(Line line, HeaderImpl header) {
        this.line = line;
        this.header = header;
    }

    @Override
    public Header getHeader() {
        if (this.header == null) {
            throw new CsvException(new GeneralError(
                    "No header has been found for this file. Set @CsvFile#useHeaders to read the header"));
        }
        return this.header;
    }

    @Override
    public RowReport reportOnEndOfLine() {
        return line.reportOnEndOfLine();
    }

    @Override
    public RowReport reportOnColumn(int columnIndex) {
        return line.reportOnColumn(new Column(columnIndex));
    }

    @Override
    public String get(String columnName) {
        return line.get(header.getIndex(columnName));
    }

    @Override
    public String getColumnName(int columnIndex) {
        return getHeader().getName(columnIndex);
    }

    @Override
    public boolean hasHeader() {
        return header != null;
    }

    @Override
    public int size() {
        return line.size();
    }

    @Override
    public String get(int columnIndex) {
        return line.get(columnIndex);
    }

    /**
    * Returns an iterator over the individual cells of a Row
    * @return iterator over the cells in String format
    */
    public Iterator<String> iterator() {
        return line.iterator();
    }

}
