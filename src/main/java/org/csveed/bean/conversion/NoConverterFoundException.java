package org.csveed.bean.conversion;

public class NoConverterFoundException extends ConversionException {

    public NoConverterFoundException(String message, Class propertyType) {
        super(message, propertyType);
    }

}
