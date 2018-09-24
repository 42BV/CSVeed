package org.csveed.bean;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

import java.util.Set;

public class ColumnIndexMapper<T> extends AbstractMapper<T> {

    @Override
    protected Set<Column> keys() {
        return beanInstructions.getProperties().columnIndexKeys();
    }

    @Override
    public BeanProperty getBeanProperty(Column currentColumn) {
        return beanInstructions.getProperties().fromIndex(currentColumn);
    }

    @Override
    protected void checkKey(Header header, Column key) {
        if (key.getColumnIndex() > header.size()) {
            throw new CsvException(new GeneralError(
                    "Column with index " + key + " does not exist in file with " + header.size() + " columns. " +
                            "Originally mapped to property \"" + getBeanProperty(key).getPropertyName() + "\""
                    ));
        }
    }

    @Override
    protected Column getColumn(Row row) {
        return new Column();
    }

}
