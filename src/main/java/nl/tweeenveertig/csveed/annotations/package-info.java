/**
* <p>Contains various annotation that can be applied to Java beans and contain instructions for reading CSV files
* and converting CSV Rows to beans.</p>
*
* <p>The following annotations can be found here:</p>
* <ul>
*     <li>{@link CsvFile}; generic instructions such as the symbols to use for parsing and whether to skip
*       comment and empty lines. Used to set the start line from which to start the parsing process and whether
*       to use headers or not. Also instructs on the usage of the mapping strategy from Row to bean.</li>
*     <li>{@link CsvCell}; used to determine the mapping from either the column index or the column name to
*       the property. Also used to set whether the value is required.</li>
*     <li>{@link CsvConverter}; used to set your own {@link java.beans.PropertyEditor} on the property</li>
*     <li>{@link CsvDate}; used to set a custom date format (see {@link java.text.SimpleDateFormat} when
*       converting to a {@link java.util.Date}</li>
*     <li>{@link CsvIgnore}; states that a property must be ignored for mapping</li>
* </ul>
*
* <p>Note that a default mapping interpretation is in place to make sure that an absolute minimum
* configuration is required:</p>
*
* <ul>
*     <li>Column Index Mapping; used by default if no mapper is set, or explicitly if
*       {@link nl.tweeenveertig.csveed.annotations.CsvFile#mappingStrategy()} is set to
*       {@link nl.tweeenveertig.csveed.bean.ColumnIndexMapper}. Properties are read in declaration order from
*       their classes which translates directly to their column index.</li>
*     <li>Column Name Mapping; used if  {@link nl.tweeenveertig.csveed.annotations.CsvFile#mappingStrategy()}
*       is set to {@link nl.tweeenveertig.csveed.bean.ColumnNameMapper}. By default the name of the property
*       translates to the column header.</li>
* </ul>
*/
package nl.tweeenveertig.csveed.annotations;