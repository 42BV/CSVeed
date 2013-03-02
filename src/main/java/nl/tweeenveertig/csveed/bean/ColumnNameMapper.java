package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.report.RowError;

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
        if (row.getHeader().getIndex(key) == null) {
            throw new CsvException(new RowError(
                    "The header row does not contain column \""+key+"\". Originally mapped to property \""+
                            getBeanProperty(key).getPropertyName()+"\"",
                    row.getHeader().reportOnEndOfLine(), 0
            ));
        }
    }

}
