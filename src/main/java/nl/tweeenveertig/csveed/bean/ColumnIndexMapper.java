package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ColumnIndexMapper<T> extends AbstractMapper<T, Integer> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnIndexMapper.class);

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
            String errorMessage =
                    "Column with index "+key+" does not exist in file with "+row.size()+" columns. "+
                    "Originally mapped to property \""+getBeanProperty(key).getPropertyName()+"\"";
            LOG.error(errorMessage);
            throw new CsvException(errorMessage);

        };
    }

}
