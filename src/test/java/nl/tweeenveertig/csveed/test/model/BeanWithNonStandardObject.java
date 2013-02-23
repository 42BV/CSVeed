package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.annotations.CsvFile;

@CsvFile(useHeader = false)
public class BeanWithNonStandardObject {

    private BeanSimple bean;

    public BeanSimple getBean() {
        return bean;
    }

    public void setBean(BeanSimple bean) {
        this.bean = bean;
    }
}
