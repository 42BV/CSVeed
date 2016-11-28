package org.csveed.bean.conversion;

public class NoConverterFoundException extends ConversionException {

    private static final long serialVersionUID = 1L;

    public NoConverterFoundException(String message, Class propertyType) {
        super(message, propertyType);
    }

}
