package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;

import java.util.Set;

public class ColumnIndexMapper<T> extends AbstractMapper<T, Integer> {

    @Override
    public BeanProperty getBeanProperty(Row row, int columnIndex) {
        return getBeanProperty(columnIndex);
    }

    @Override
    protected Set<Integer> keys() {
        return beanReaderInstructions.getProperties().columnIndexKeys();
    }

    protected BeanProperty getBeanProperty(int columnIndex) {
        return beanReaderInstructions.getProperties().fromIndex(columnIndex);
    }

    @Override
    protected void checkKey(Row row, Integer key) {
        if (key < 0 || key >= row.size()) {
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
