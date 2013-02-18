package nl.tweeenveertig.csveed.testclasses;

import nl.tweeenveertig.csveed.bean.annotations.CsvFile;

@CsvFile(useHeader = false)
public class BeanWithoutNoArgPublicConstructor {

    private String name;

    public BeanWithoutNoArgPublicConstructor(String name) {
        this.name = this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
