package nl.tweeenveertig.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Various settings applying to the entire CSV file and Bean.
* @author Robert Bor
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvFile {

    /**
    * The quote symbol is the sign on both sides of the field value. By default this will be a double quote
    * @return quote symbol for a field
    */
    String quote() default "\"";

    /**
     * The delimiter is the symbol between two fields. By default this will be a semi-colon
     * @return delimiter between two fields
     */
    String delimiter() default ";";

    /**
    * The symbols all eligible as end-of-line markers. By default \r and \n are both eol symbols
    * @return all the eligible eol symbols
    */
    String[] endOfLine() default { "\r", "\n" };

    /**
    * States whether the first line will be used as a header line. If this is not the case, the mapping
    * will be done based on column indexes.
    * @return whether to use the first line as a header or not
    */
    boolean useHeader() default true;

    /**
    * The point from where the lines must be read. By default, this value is 1. Note that if UseHeader
    * is not used, this value must be changed
    * @return the point from where lines must be converted
    */
    int startRow() default 1;

}
