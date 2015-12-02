package org.csveed.annotations;

import org.csveed.bean.AbstractMapper;
import org.csveed.bean.ColumnIndexMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Various settings applying to the entire CSV file and BeanInstructions.
* @author Robert Bor
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvFile {

    /**
    * The symbol which escapes a quote character while inside a quoted string. By default a double quote (")
    * @return escape character
    */
    char escape() default '"';

    /**
    * The quote symbol is the sign on both sides of the field value. By default this will be a double quote
    * @return quote symbol for a field
    */
    char quote() default '"';

    /**
    * The separator is the symbol between two fields. By default this will be a semi-colon
    * @return separator between two fields
    */
    char separator() default ';';

    /**
    * The symbols all eligible as end-of-line markers. By default \r and \n are both eol symbols
    * @return all the eligible eol symbols
    */
    char[] endOfLine() default { '\r', '\n' };

    /**
    * All lines starting with this symbol (must be at the first encountered position) will be considered
    * comments, which are ignored by the parser
    * @return comment symbol
    */
    char comment() default '#';

    /**
    * States whether the first line will be used as a structure line. If this is not the case, the mapping
    * will be done based on column indexes.
    * @return whether to use the first line as a structure or not
    */
    boolean useHeader() default true;

    /**
    * The point from where the lines must be read, including the structure (if applicable). By default,
    * this value is 1 and includes the header. Note that row counting starts at 1, not at 0, ie CSVeed is
    * 1-based to be more aligned with the Excel user who receives the error report, not the developer.
    * @return the point from where lines must be converted, 1-based
    */
    int startRow() default 1;

    /**
    * Ascertains that empty lines are skipped. If this value is false, empty lines will be parsed as single
    * column rows. Default values is true.
    * @return whether empty files must be skipped
    */
    boolean skipEmptyLines() default true;

    /**
    * Ascertains that comment lines are skipped. If this value is false, the comment marker is ignored.
    * Normally, this method should not be needed. Use only if you want to have 100% certainty that lines
    * identified as comment lines are never skipped. Default value is true.
    * @return whether comment lines (marked with a comment marker on the first position) must be skipped
    */
    boolean skipCommentLines() default true;

    /**
    * The column where the dynamic headers start. All columns following this column are automatically assumed
    * to be dynamic also.
    * @return index where dynamic columns start
    */
    int startIndexDynamicColumns() default 0;

    /**
    * Determines the strategy to employ for mapping between CSV and Bean. The default will be to map on the
    * basis of the column index
    * @return the mapping strategy
    */
    Class<? extends AbstractMapper> mappingStrategy() default ColumnIndexMapper.class;

}
