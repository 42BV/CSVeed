package org.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* When this annotation is set on a field, it will be skipped during deserialization even if a matching column
* name is found. On serialization, it will not be part of the new CSV file.
* @author Robert Bor
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvIgnore {
}
