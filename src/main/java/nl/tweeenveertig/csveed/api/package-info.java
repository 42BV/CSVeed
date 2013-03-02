/**
* The main interface of interaction with CSVeed for a developer. The most important interface here is {@link CsvReader}
* which if a Facade for both {@link nl.tweeenveertig.csveed.bean.BeanReader} and
* {@link nl.tweeenveertig.csveed.row.RowReader}. There should be no need for accessing those two directly, although
* it is possible.
*/
package nl.tweeenveertig.csveed.api;