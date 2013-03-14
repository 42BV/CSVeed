package org.csveed.bean.conversion;

public abstract class ConversionException extends Exception {

    private String typeDescription;

    public ConversionException(String message, Class clazz) {
        this(message, clazz.getName(), null);
    }

    public ConversionException(String message, String typeDescription, Throwable exception) {
        super(message, exception);
        this.typeDescription = typeDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}
