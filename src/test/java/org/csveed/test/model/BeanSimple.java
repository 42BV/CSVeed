package org.csveed.test.model;

import org.csveed.test.annotations.IgnoreClass;
import org.csveed.test.annotations.IgnoreField;

@IgnoreClass
public class BeanSimple {

    @IgnoreField
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
