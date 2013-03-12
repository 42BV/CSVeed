package nl.tweeenveertig.csveed.bean.conversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static nl.tweeenveertig.csveed.bean.conversion.ConversionUtil.hasText;

public class DateConverter implements Converter<Date> {

    private final DateFormat dateFormat;

    private final boolean allowEmpty;

    private final int exactDateLength;

    public DateConverter(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }

    public DateConverter(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }

    @Override
    public Date fromString(String text) throws Exception {
        if (this.allowEmpty && !hasText(text)) {
            return null;
        }
        else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
            throw new IllegalArgumentException(
                    "Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
        }
        else {
            try {
                return this.dateFormat.parse(text);
            }
            catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

//    @Override
//    public String toString(Date value) {
//        return value != null ? this.dateFormat.format(value) : "";
//    }

}
