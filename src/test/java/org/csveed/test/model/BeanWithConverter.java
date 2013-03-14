package org.csveed.test.model;

import org.csveed.annotations.CsvConverter;
import org.csveed.annotations.CsvFile;
import org.csveed.test.converters.BeanSimpleConverter;

@CsvFile(useHeader = false)
public class BeanWithConverter {

    @CsvConverter(converter = BeanSimpleConverter.class)
    private BeanSimple bean;

    public BeanSimple getBean() {
        return bean;
    }

    public void setBean(BeanSimple bean) {
        this.bean = bean;
    }
}
