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
            String errorMessage =
                    "The header row does not contain column \""+key+"\". Originally mapped to property \""+
                    getBeanProperty(key).getPropertyName()+"\"";
            LOG.error(errorMessage);
            for (String line : row.getHeader().reportOnEndOfLine().getPrintableLines()) {
                LOG.error(line);
            }
            throw new CsvException(errorMessage, null, row.getHeader().reportOnEndOfLine(), 0);
        }
    }

}
