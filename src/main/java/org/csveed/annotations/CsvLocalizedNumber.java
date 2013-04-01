package org.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Makes sure that a specific Locale is used to convert numbers. If no Locale is required, no annotation
* needs to be set, because the right converter will be picked up automatically. If you still wish to
* apply a custom converter, use {@link CsvConverter}.
* @author Robert Bor
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvLocalizedNumber {

    /**
    * Language used to construct Locale
    */
    String language();

    /**
    * Country used to construct Locale
    */
    String country() default "";

    /**
    * Variant used to construct Locale
    */
    String variant() default "";
}
