package org.csveed.test.converters;

import org.csveed.bean.conversion.AbstractConverter;
import org.csveed.test.model.BeanSimple;

public class BeanSimpleConverter extends AbstractConverter<BeanSimple> {

    public BeanSimpleConverter() {
        super(BeanSimple.class);
    }

    @Override
    public BeanSimple fromString(String text) throws Exception {
        BeanSimple bean = new BeanSimple();
        bean.setName(text);
        return bean;
    }

    @Override
    public String toString(BeanSimple value) throws Exception {
        return value.getName();
    }

}
