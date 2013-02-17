package nl.tweeenveertig.csveed.csv.structure;

public interface Row extends Iterable<String> {

    public int size();

    public String get(int index);

    public RowReport reportOnEndOfLine();

    public RowReport reportOnColumn(int columnIndex);

}
