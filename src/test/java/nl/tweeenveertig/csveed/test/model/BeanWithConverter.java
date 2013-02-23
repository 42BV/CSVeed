package nl.tweeenveertig.csveed.test.model;

import nl.tweeenveertig.csveed.annotations.CsvConverter;
import nl.tweeenveertig.csveed.annotations.CsvFile;
import nl.tweeenveertig.csveed.test.propertyeditors.BeanSimplePropertyEditor;

@CsvFile(useHeader = false)
public class BeanWithConverter {

    @CsvConverter(converter = BeanSimplePropertyEditor.class)
    private BeanSimple bean;

    public BeanSimple getBean() {
        return bean;
    }

    public void setBean(BeanSimple bean) {
        this.bean = bean;
    }
}
