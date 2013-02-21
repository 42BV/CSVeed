package nl.tweeenveertig.csveed.csv.structure;

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
