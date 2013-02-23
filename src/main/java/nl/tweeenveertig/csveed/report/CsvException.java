package nl.tweeenveertig.csveed.report;

public class CsvException extends RuntimeException {

    private RowReport report;

    private int lineNumber;

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable err) {
        super(message, err);
    }

    public CsvException(String message, Throwable err, RowReport report, int lineNumber) {
        super(message, err);
        this.report = report;
    }

    public RowReport getReport() {
        return this.report;
    }

    public int getLineNumber() {
        return lineNumber;
    }

//    public String getMessage() {
//        StringBuilder returnString = new StringBuilder();
//        returnString.append(super.getMessage());
//        for (String line : getReport().getPrintableLines()) {
//            returnString.append("\n"+lineNumber+": "+line);
//        }
//        return returnString.toString();
//    }

}
