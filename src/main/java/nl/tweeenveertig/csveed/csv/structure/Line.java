package nl.tweeenveertig.csveed.csv.structure;

public interface Line extends Iterable<String> {

    int size();

    String get(int index);

    RowReport reportOnEndOfLine();

    RowReport reportOnColumn(int columnIndex);

}
