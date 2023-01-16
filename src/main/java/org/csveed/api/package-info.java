/**
 * The main interface of interaction with CSVeed for a developer. The most important interface here is
 * {@link org.csveed.api.CsvClient} which if a Facade for both {@link org.csveed.bean.BeanReader} and
 * {@link org.csveed.row.RowReader}. There should be no need for accessing those two directly, although it is possible.
 */
package org.csveed.api;