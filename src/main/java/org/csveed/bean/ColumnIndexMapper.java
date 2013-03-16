package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.util.ExcelColumn;

import java.util.Set;

public class ColumnIndexMapper<T> extends AbstractMapper<T, ExcelColumn> {

    @Override
    public BeanProperty getBeanProperty(Row row, ExcelColumn currentColumn) {
        return getBeanProperty(currentColumn);
    }

    @Override
    protected Set<ExcelColumn> keys() {
        return beanReaderInstructions.getProperties().columnIndexKeys();
    }

    protected BeanProperty getBeanProperty(ExcelColumn currentColumn) {
        return beanReaderInstructions.getProperties().fromIndex(currentColumn);
    }

    @Override
    protected void checkKey(Row row, ExcelColumn key) {
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
