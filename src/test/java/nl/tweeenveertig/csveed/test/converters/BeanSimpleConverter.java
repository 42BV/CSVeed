package nl.tweeenveertig.csveed.test.converters;

import nl.tweeenveertig.csveed.bean.conversion.Converter;
import nl.tweeenveertig.csveed.test.model.BeanSimple;

public class BeanSimpleConverter implements Converter<BeanSimple> {

    @Override
    public BeanSimple fromString(String text) throws Exception {
        BeanSimple bean = new BeanSimple();
        bean.setName(text);
        return bean;
    }
}
