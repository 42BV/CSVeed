package nl.tweeenveertig.csveed.api;

import nl.tweeenveertig.csveed.report.RowReport;

public interface Header extends Iterable<String> {

    public int size();

    public String getName(Integer indexColumn);

    public Integer getIndex(String columnName);

    public RowReport reportOnEndOfLine();

}
