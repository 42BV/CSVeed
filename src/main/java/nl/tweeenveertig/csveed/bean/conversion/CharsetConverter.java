package nl.tweeenveertig.csveed.bean.conversion;

import java.nio.charset.Charset;

public class CharsetConverter implements Converter<Charset> {

    @Override
    public Charset fromString(String text) throws Exception {
        if (text != null && !text.equals("")) {
            return Charset.forName(text);
        } else {
            return null;
        }
    }

    @Override
    public String toString(Charset value) throws Exception {
        return value != null ? value.name() : "";
    }

}
