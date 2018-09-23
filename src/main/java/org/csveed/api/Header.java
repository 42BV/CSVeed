package org.csveed.api;

import org.csveed.report.RowReport;

/**
* The original header of the CSV file
* @author Robert Bor
*/
public interface Header extends Iterable<String> {

    /**
    * Number of columns
    * @return the number of columns
    */
    int size();

    /**
    * Gets the name of the header column with passed index
    * @param indexColumn column index to find the name for
    * @return name of the header column
    */
    String getName(int indexColumn);

    /**
    * Gets the index column of the first column with a certain name
    * @param columnName column name to find the index for
    * @return index of the header column
    */
    int getIndex(String columnName);

    /**
    * Generate an error report on the header row
    * @return error report on the header row
    */
    RowReport reportOnEndOfLine();

}
