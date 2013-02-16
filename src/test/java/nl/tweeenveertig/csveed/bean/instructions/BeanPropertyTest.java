package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.testclasses.BeanSimple;
import org.junit.Test;
import org.springframework.beans.propertyeditors.URLEditor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BeanPropertyTest {

    @Test
    public void construct() throws IntrospectionException {
        BeanProperty property = new BeanProperty();
        PropertyEditor editor = new URLEditor();
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("name", BeanSimple.class);
        property.setConverter(editor);
        property.setIndexColumn(3);
        property.setName("name");
        property.setPropertyDescriptor(propertyDescriptor);
        property.setRequired(true);
        assertEquals(editor, property.getConverter());
        assertEquals(3, property.getIndexColumn());
        assertEquals("name", property.getName());
        assertEquals(propertyDescriptor, property.getPropertyDescriptor());
        assertTrue(property.isRequired());
    }

}
