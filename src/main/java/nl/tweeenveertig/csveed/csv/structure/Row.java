package nl.tweeenveertig.csveed.csv.structure;

public interface Row extends Iterable<String> {

    public int size();

    public String get(int index);

}
