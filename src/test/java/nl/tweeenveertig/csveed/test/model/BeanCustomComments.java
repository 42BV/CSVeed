package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.annotations.CsvFile;
import nl.tweeenveertig.csveed.test.annotations.IgnoreClass;
import nl.tweeenveertig.csveed.test.annotations.IgnoreField;

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
