package nl.tweeenveertig.csveed.testclasses;

import nl.tweeenveertig.csveed.bean.annotations.CsvFile;

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
