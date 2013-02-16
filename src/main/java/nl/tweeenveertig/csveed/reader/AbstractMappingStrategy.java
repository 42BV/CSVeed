package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import org.springframework.beans.BeanWrapperImpl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractMappingStrategy<T> {

    protected Map<Integer, BeanProperty> indexToProperty = new TreeMap<Integer, BeanProperty>();

    public BeanProperty getBeanProperty(int indexColumn) {
        return indexToProperty.get(indexColumn);
    }

    public T convert(T bean, List<String> line) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);

        int indexColumn = 0;
        for (String cell : line) {
            BeanProperty beanProperty = getBeanProperty(indexColumn);
            if (beanProperty == null) {
                // error condition
                continue;
            }
            if (beanProperty.getConverter() != null) {
                beanWrapper.registerCustomEditor(
                        beanProperty.getPropertyDescriptor().getPropertyType(),
                        beanProperty.getPropertyDescriptor().getName(), // ascertain the converter is only used on this property
                        beanProperty.getConverter());
            }
            beanWrapper.setPropertyValue(beanProperty.getPropertyDescriptor().getName(), cell);
            indexColumn++;
        }
        return bean;
    }

}
