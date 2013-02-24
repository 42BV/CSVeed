package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;

public class ColumnNameMapper<T> extends AbstractMapper<T> {

    @Override
    public BeanProperty getBeanProperty(Row row, int columnIndex) {
        String columnName = row.getHeader().getName(columnIndex);
        return beanReaderInstructions.getProperties().fromName(columnName);
    }

}
