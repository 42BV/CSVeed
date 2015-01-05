package org.csveed.bean.conversion;

public class BeanPropertyConversionException extends ConversionException {

    private static final long serialVersionUID = 1L;

    public BeanPropertyConversionException(String message, String propertyDescription, Throwable exception) {
        super(message, propertyDescription, exception);
    }

}
