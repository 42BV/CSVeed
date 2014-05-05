package org.csveed.api;

public interface CsvWriter<T> {

    /**
    * Writes a single row to the Writer.
    */
    public void writeRow(Row row);


}
