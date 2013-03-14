package nl.tweeenveertig.csveed.bean.conversion;

public class StringConverter extends AbstractConverter<String> {

    @Override
    public String fromString(String text) {
        return text;
    }

    @Override
    public Class getType() {
        return String.class;
    }

//    @Override
//    public String toString(String value) {
//        return value;
//    }

}
