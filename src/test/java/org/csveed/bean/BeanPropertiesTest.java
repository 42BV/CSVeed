package org.csveed.bean;

import org.csveed.report.CsvException;
import org.csveed.test.model.BeanSimple;
import org.junit.Test;

public class BeanPropertiesTest {

    @Test(expected = CsvException.class)
    public void mapAtColumnIndex0() {
        BeanProperties properties = new BeanProperties(BeanSimple.class);
        properties.mapIndexToProperty(0, "name");
    }
}
