package org.csveed.test.model;

import org.csveed.annotations.CsvLocalizedNumber;

public class BeanWithCustomNumberAnnotated {

    @CsvLocalizedNumber(language = "de", country = "DE")
    private Double number;

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }
}
