package org.csveed.bean.conversion;

/**
 * Stateless converter from String to Object
 *
 * @param <K>
 *            the Object to convert the String to
 *
 * @author Robert Bor
 */
public interface Converter<K> {

    K fromString(String text) throws Exception;

    String toString(K value) throws Exception;

    String infoOnType();

    Class<K> getType();

}
