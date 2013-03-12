package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.annotations.CsvConverter;
import nl.tweeenveertig.csveed.annotations.CsvFile;
import nl.tweeenveertig.csveed.test.converters.BeanSimpleConverter;

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
