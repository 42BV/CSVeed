package nl.tweeenveertig.csveed.test.converters;

import nl.tweeenveertig.csveed.bean.conversion.AbstractConverter;
import nl.tweeenveertig.csveed.test.model.BeanSimple;

public class BeanSimpleConverter extends AbstractConverter<BeanSimple> {

    @Override
    public BeanSimple fromString(String text) throws Exception {
        BeanSimple bean = new BeanSimple();
        bean.setName(text);
        return bean;
    }

    @Override
    public String infoOnType() {
        return getType(BeanSimple.class);
    }

}
