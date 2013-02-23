package nl.tweeenveertig.csveed.annotations;

import java.beans.PropertyEditor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Sets a custom converter for the field. The converter must be of type PropertyEditor.
* @author Robert Bor
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvConverter {

    /**
    * The PropertyEditor to use for the field
    * @return PropertyEditor
    */
    Class<? extends PropertyEditor> converter();

}
