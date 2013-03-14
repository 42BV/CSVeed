package org.csveed.test.model;

import org.csveed.annotations.CsvFile;

@CsvFile(comment = '%')
public class BeanCustomComments {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
