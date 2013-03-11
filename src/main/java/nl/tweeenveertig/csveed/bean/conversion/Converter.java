package nl.tweeenveertig.csveed.bean.conversion;

public interface Converter<K> {

    K fromString(String text) throws Exception;

    String toString(K value) throws Exception;

}
