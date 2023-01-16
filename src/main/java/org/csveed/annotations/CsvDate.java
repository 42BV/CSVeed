package org.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date is a special case, since it will require a format to be supplied. The format is similar to the one used in
 * SimpleDateFormat. As a matter of fact, this class is used under the hood to arrange the conversion.
 *
 * @author Robert Bor
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvDate {

    /**
     * The format to use for converting between String and java.util.Date
     *
     * @return format for the Date
     */
    String format() default "yyyy-MM-dd";
}
