package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;

import java.util.Set;

public class ColumnNameMapper<T> extends AbstractMapper<T> {

    @Override
    protected Set<Column> keys() {
        return beanReaderInstructions.getProperties().columnNameKeys();
    }

    @Override
    public BeanProperty getBeanProperty(Column column) {
        return beanReaderInstructions.getProperties().fromName(column);
    }

    @Override
    protected void checkKey(Row row, Column key) {
        try {
            row.getHeader().getIndex(key.getColumnName());
        } catch (CsvException err) {
            throw new CsvException(new RowError(
                    "The header row does not contain column \""+key+"\". Originally mapped to property \""+
                            getBeanProperty(key).getPropertyName()+"\"",
                    row.getHeader().reportOnEndOfLine(), 0
            ));
        }
    }

    @Override
    protected Column getColumn(Row row) {
        return new Column().setHeader(row.getHeader());
    }

}
