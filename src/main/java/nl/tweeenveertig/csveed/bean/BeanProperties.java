package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.*;
import java.lang.reflect.Field;
import java.util.*;

public class BeanProperties implements Iterable<BeanProperty> {

    public static final Logger LOG = LoggerFactory.getLogger(BeanParser.class);

    private List<BeanProperty> properties = new ArrayList<BeanProperty>();

    private Map<Integer, BeanProperty> indexToProperty = new TreeMap<Integer, BeanProperty>();
    private Map<String, BeanProperty> nameToProperty = new TreeMap<String, BeanProperty>();

    private Class beanClass;

    public BeanProperties(Class beanClass) {
        this.beanClass = beanClass;
        parseBean(beanClass);
    }

    private void parseBean(Class beanClass) {
        final BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException err) {
            throw new RuntimeException(err);
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        // Note that we use getDeclaredFields here instead of the PropertyDescriptor order. The order we now
        // use is guaranteed to be the declaration order from JDK 6+, which is exactly what we need.
        for(Field field : beanClass.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyDescriptors, field);
            if (propertyDescriptor == null || propertyDescriptor.getWriteMethod() == null) {
                LOG.info("Skipping "+beanClass.getName()+"."+field.getName());
                continue;
            }
            addProperty(propertyDescriptor, field);
        }
    }

    private void addProperty(PropertyDescriptor propertyDescriptor, Field field) {
        BeanProperty beanProperty = new BeanProperty();
        beanProperty.setPropertyDescriptor(propertyDescriptor);
        beanProperty.setField(field);
        this.properties.add(beanProperty);
    }

    private PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, Field field) {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (field.getName().equals(propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    public void setRequired(String propertyName, boolean required) {
        get(propertyName).setRequired(required);
    }

    public void setConverter(String propertyName, PropertyEditor converter) {
        get(propertyName).setConverter(converter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public void ignoreProperty(String propertyName) {
        BeanProperty property = get(propertyName);
        properties.remove(property);
        while (indexToProperty.values().remove(property));
        while (nameToProperty.values().remove(property));
    }

    public void mapIndexToProperty(int columnIndex, String propertyName) {
        BeanProperty property = get(propertyName);
        property.setColumnIndex(columnIndex);
        indexToProperty.put(columnIndex, get(propertyName));
    }

    public void mapNameToProperty(String columnName, String propertyName) {
        BeanProperty property = get(propertyName);
        property.setColumnName(columnName);
        nameToProperty.put(columnName, get(propertyName));
    }

    protected BeanProperty get(String propertyName) {
        for (BeanProperty beanProperty : properties) {
            if (beanProperty.getPropertyName().equals(propertyName)) {
                return beanProperty;
            }
        }
        String message = "Property does not exist: "+ beanClass.getName()+"."+propertyName;
        LOG.error(message);
        throw new CsvException(message);
    }

    public BeanProperty fromIndex(int columnIndex) {
        return indexToProperty.get(columnIndex);
    }

    public BeanProperty fromName(String columnName) {
        return nameToProperty.get(columnName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<BeanProperty> iterator() {
        return ((List<BeanProperty>)((ArrayList)this.properties).clone()).iterator();
    }
}
