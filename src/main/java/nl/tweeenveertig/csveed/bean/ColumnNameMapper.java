package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ColumnNameMapper<T> extends AbstractMapper<T, String> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnNameMapper.class);

    @Override
    public BeanProperty getBeanProperty(Row row, int columnIndex) {
        String columnName = row.getHeader().getName(columnIndex);
        return beanReaderInstructions.getProperties().fromName(columnName);
    }

    @Override
    protected Set<String> keys() {
        return beanReaderInstructions.getProperties().columnNameKeys();
    }

    @Override
    protected void checkKey(Row row, String key) {
        if (row.getHeader().getIndex(key) == null) {
            String errorMessage =
                    "The header does not contain field \""+key+"\". Originally mapped to property \""+
                    beanReaderInstructions.getProperties().fromName(key).getPropertyName()+"\"";
            LOG.error(errorMessage);
            throw new CsvException(errorMessage);
        }
    }

}
