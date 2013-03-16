package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

import java.util.Set;

public class ColumnIndexMapper<T> extends AbstractMapper<T, Column> {

    @Override
    public BeanProperty getBeanProperty(Row row, Column currentColumn) {
        return getBeanProperty(currentColumn);
    }

    @Override
    protected Set<Column> keys() {
        return beanReaderInstructions.getProperties().columnIndexKeys();
    }

    protected BeanProperty getBeanProperty(Column currentColumn) {
        return beanReaderInstructions.getProperties().fromIndex(currentColumn);
    }

    @Override
    protected void checkKey(Row row, Column key) {
        if (key.getColumnIndex() > row.size()) {
            throw new CsvException(new GeneralError(
                    "Column with index "+key+" does not exist in file with "+row.size()+" columns. "+
                    "Originally mapped to property \""+getBeanProperty(key).getPropertyName()+"\""
            ));
        };
    }

    @Override
    public String getColumnIdentifier(BeanProperty beanProperty) {
        return "index "+Integer.toString(beanProperty.getColumnIndex());
    }

}
