package nl.tweeenveertig.csveed.csv.structure;

public class RowPart {

    private String token;

    private boolean highlight = false;

    public RowPart(String token, boolean highlight) {
        this.token = token;
        this.highlight = highlight;
    }

    public String getToken() {
        return token;
    }

    public boolean isHighlight() {
        return highlight;
    }
}
