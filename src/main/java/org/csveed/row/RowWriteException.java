package org.csveed.row;

public class RowWriteException extends RuntimeException {

    public RowWriteException(Throwable ex, String errorMessage) {
        super(errorMessage, ex);
    }

    public RowWriteException(String errorMessage) {
        super(errorMessage);
    }

}
