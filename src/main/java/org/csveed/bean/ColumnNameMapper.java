package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;

import java.util.Set;

public class ColumnNameMapper<T> extends AbstractMapper<T, String> {

    @Override
    public BeanProperty getBeanProperty(Row row, int columnIndex) {
        return getBeanProperty(row.getHeader().getName(columnIndex));
    }

    @Override
    protected Set<String> keys() {
        return beanReaderInstructions.getProperties().columnNameKeys();
    }

    protected BeanProperty getBeanProperty(String columnName) {
        return beanReaderInstructions.getProperties().fromName(columnName);
    }

    @Override
    protected void checkKey(Row row, String key) {
        try {
            row.getHeader().getIndex(key);
        } catch (CsvException err) {
            throw new CsvException(new RowError(
                    "The header row does not contain column \""+key+"\". Originally mapped to property \""+
                            getBeanProperty(key).getPropertyName()+"\"",
                    row.getHeader().reportOnEndOfLine(), 0
            ));
        }
    }

    @Override
    public String getColumnIdentifier(BeanProperty beanProperty) {
        return "name \"" + beanProperty.getColumnName() + "\"";
    }

}
