package org.csveed.test.model;

import org.csveed.annotations.CsvLocalizedNumber;

public class BeanWithWrongAnnotation {

    @CsvLocalizedNumber(language = "EN")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
