package nl.tweeenveertig.csveed.test.propertyeditors;

import nl.tweeenveertig.csveed.test.model.BeanSimple;

import java.beans.PropertyEditorSupport;

public class BeanSimplePropertyEditor extends PropertyEditorSupport {

    public String getAsText() {
        return ((BeanSimple)getValue()).getName();
    }

    public void setAsText(String text) {
        BeanSimple bean = new BeanSimple();
        bean.setName(text);
        setValue(bean);
    }

}
