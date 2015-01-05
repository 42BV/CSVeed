package org.csveed.test.model;

import org.csveed.annotations.CsvFile;

@CsvFile(useHeader = false)
public class BeanWithoutNoArgPublicConstructor {

    private String name;

    public BeanWithoutNoArgPublicConstructor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
