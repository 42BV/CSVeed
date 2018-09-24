package org.csveed.bean.conversion;

import static org.csveed.bean.conversion.ConversionUtil.hasLength;

public class CustomBooleanConverter extends AbstractConverter<Boolean> {

    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";

    private final String trueString;

    private final String falseString;

    private final boolean allowEmpty;

    public CustomBooleanConverter(boolean allowEmpty) {
        this(null, null, allowEmpty);
    }

    public CustomBooleanConverter(String trueString, String falseString, boolean allowEmpty) {
        super(Boolean.class);
        this.trueString = trueString;
        this.falseString = falseString;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Boolean fromString(String text) throws Exception {
        String input = text != null ? text.trim() : null;
        if (this.allowEmpty && !hasLength(input)) {
            return null;
        } else if (this.trueString != null && this.trueString.equalsIgnoreCase(input)) {
            return Boolean.TRUE;
        } else if (this.falseString != null && this.falseString.equalsIgnoreCase(input)) {
            return Boolean.FALSE;
        } else if (this.trueString == null &&
                (VALUE_TRUE.equalsIgnoreCase(input) || VALUE_ON.equalsIgnoreCase(input) ||
                        VALUE_YES.equalsIgnoreCase(input) || VALUE_1.equals(input))) {
            return Boolean.TRUE;
        } else if (this.falseString == null &&
                (VALUE_FALSE.equalsIgnoreCase(input) || VALUE_OFF.equalsIgnoreCase(input) ||
                        VALUE_NO.equalsIgnoreCase(input) || VALUE_0.equals(input))) {
            return Boolean.FALSE;
        } else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }

    @Override
    public String toString(Boolean value) throws Exception {
        if (Boolean.TRUE.equals(value)) {
            return this.trueString != null ? this.trueString : VALUE_TRUE;
        } else if (Boolean.FALSE.equals(value)) {
            return this.falseString != null ? this.falseString : VALUE_FALSE;
        } else {
            return "";
        }
    }

}
