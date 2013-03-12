package nl.tweeenveertig.csveed.bean.conversion;

public class StringConverter implements Converter<String> {

    @Override
    public String fromString(String text) {
        return text;
    }

//    @Override
//    public String toString(String value) {
//        return value;
//    }

}
