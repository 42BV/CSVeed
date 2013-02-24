package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;

public class ColumnIndexMapper<T> extends AbstractMapper<T> {

    @Override
    public BeanProperty getBeanProperty(Row row, int columnIndex) {
        return beanReaderInstructions.getProperties().fromIndex(columnIndex);
    }

}
