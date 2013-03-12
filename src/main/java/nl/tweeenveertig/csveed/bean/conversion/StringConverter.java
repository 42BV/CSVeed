package nl.tweeenveertig.csveed.bean.conversion;

public class StringConverter extends AbstractConverter<String> {

    @Override
    public String fromString(String text) {
        return text;
    }

    @Override
    public String infoOnType() {
        return getType(String.class);
    }

//    @Override
//    public String toString(String value) {
//        return value;
//    }

}
