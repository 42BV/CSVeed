package nl.tweeenveertig.csveed.annotations;

import nl.tweeenveertig.csveed.bean.conversion.Converter;

import java.beans.PropertyEditor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Sets a custom converter for the field. The converter must be of type Converter.
* @author Robert Bor
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvConverter {

    /**
    * The Converter to use for the field
    * @return PropertyEditor
    */
    Class<? extends Converter> converter();

}
