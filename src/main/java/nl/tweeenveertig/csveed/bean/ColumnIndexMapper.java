package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnIndexMapper<T> extends AbstractMapper<T> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnIndexMapper.class);

    public void instruct(BeanReaderInstructionsImpl beanReaderInstructions, Row row) {

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = beanReaderInstructions.getBeanPropertyWithIndex(indexColumn);
            if (beanProperty == null) {
                LOG.info("Column index "+indexColumn+": no match");
                indexColumn++;
                continue;
            }
            indexToProperty.put(indexColumn, beanProperty);
            String headerColumn = null;
            if (row.hasHeader()) {
                headerColumn = row.getColumnName(indexColumn);
            }
            LOG.info("Column index "+indexColumn+": "+(headerColumn == null ? "" : "[" + headerColumn + "] to [")+beanProperty.getPropertyName()+"]");
            indexColumn++;
        }
    }

}
