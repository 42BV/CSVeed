package nl.tweeenveertig.csveed.bean.conversion;

import static nl.tweeenveertig.csveed.bean.conversion.ConversionUtil.hasLength;

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
        this.trueString = trueString;
        this.falseString = falseString;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public Boolean fromString(String text) throws Exception {
        String input = (text != null ? text.trim() : null);
        if (this.allowEmpty && !hasLength(input)) {
            return null;
        }
        else if (this.trueString != null && input.equalsIgnoreCase(this.trueString)) {
            return Boolean.TRUE;
        }
        else if (this.falseString != null && input.equalsIgnoreCase(this.falseString)) {
            return Boolean.FALSE;
        }
        else if (this.trueString == null &&
                (input.equalsIgnoreCase(VALUE_TRUE) || input.equalsIgnoreCase(VALUE_ON) ||
                        input.equalsIgnoreCase(VALUE_YES) || input.equals(VALUE_1))) {
            return Boolean.TRUE;
        }
        else if (this.falseString == null &&
                (input.equalsIgnoreCase(VALUE_FALSE) || input.equalsIgnoreCase(VALUE_OFF) ||
                        input.equalsIgnoreCase(VALUE_NO) || input.equals(VALUE_0))) {
            return Boolean.FALSE;
        }
        else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }

    @Override
    public String infoOnType() {
        return getType(Boolean.class);
    }

//    @Override
//    public String toString(Boolean value) throws Exception {
//        if (Boolean.TRUE.equals(value)) {
//            return (this.trueString != null ? this.trueString : VALUE_TRUE);
//        }
//        else if (Boolean.FALSE.equals(value)) {
//            return (this.falseString != null ? this.falseString : VALUE_FALSE);
//        }
//        else {
//            return "";
//        }
//    }

}
