package nl.tweeenveertig.csveed.bean.conversion;

import java.nio.charset.Charset;

public class CharsetConverter extends AbstractConverter<Charset> {

    @Override
    public Charset fromString(String text) throws Exception {
        if (text != null && !text.equals("")) {
            return Charset.forName(text);
        } else {
            return null;
        }
    }

    @Override
    public String infoOnType() {
        return getType(Charset.class);
    }

//    @Override
//    public String toString(Charset value) throws Exception {
//        return value != null ? value.name() : "";
//    }

}
