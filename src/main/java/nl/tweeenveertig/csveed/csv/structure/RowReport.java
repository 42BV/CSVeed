package nl.tweeenveertig.csveed.csv.structure;

public class RowReport {

    private String row;

    private int start;

    private int end;

    public RowReport(String row, int start, int end) {
        this.row = row;
        this.start = start;
        this.end = end;
    }

    public String getRow() {
        return row;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
