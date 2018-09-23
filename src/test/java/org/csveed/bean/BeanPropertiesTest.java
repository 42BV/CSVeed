package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.report.CsvException;
import org.csveed.test.model.BeanSimple;
import org.junit.jupiter.api.Test;

public class BeanPropertiesTest {

    @Test
    public void mapAtColumnIndex0() {
        BeanProperties properties = new BeanProperties(BeanSimple.class);
        assertThrows(CsvException.class, () ->  {
            properties.mapIndexToProperty(0, "name");
        });
    }
}
