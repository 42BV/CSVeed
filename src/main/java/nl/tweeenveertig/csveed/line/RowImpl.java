package nl.tweeenveertig.csveed.line;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.report.RowReport;

import java.util.Iterator;

public class RowImpl implements Row {

    private Line line;

    private Header header;

    public RowImpl(Line line, Header header) {
        this.line = line;
        this.header = header;
    }

    public Header getHeader() {
        if (this.header == null) {
            throw new CsvException("No header has been found for this file");
        }
        return this.header;
    }

    public RowReport reportOnEndOfLine() {
        return line.reportOnEndOfLine();
    }

    public RowReport reportOnColumn(int columnIndex) {
        return line.reportOnColumn(columnIndex);
    }

    public String get(String columnName) {
        return line.get(header.getIndex(columnName));
    }

    @Override
    public String getColumnName(int index) {
        return getHeader().getName(index);
    }

    @Override
    public boolean hasHeader() {
        return header != null;
    }

    public int size() {
        return line.size();
    }

    public String get(int index) {
        return line.get(index);
    }

    public Iterator<String> iterator() {
        return line.iterator();
    }

    public boolean validNumberOfColumns() {
        return !hasHeader() || getHeader().checkLine(line);
    }

}
