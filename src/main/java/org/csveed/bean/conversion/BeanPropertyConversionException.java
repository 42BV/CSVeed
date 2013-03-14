package org.csveed.bean.conversion;

public class BeanPropertyConversionException extends ConversionException {

    public BeanPropertyConversionException(String message, String propertyDescription, Throwable exception) {
        super(message, propertyDescription, exception);
    }

}
