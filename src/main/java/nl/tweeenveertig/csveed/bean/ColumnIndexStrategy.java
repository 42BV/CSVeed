package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnIndexStrategy<T> extends AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnIndexStrategy.class);

    public void instruct(BeanReaderInstructionsImpl beanReaderInstructionsImpl, Row row) {

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = beanReaderInstructionsImpl.getBeanPropertyWithIndex(indexColumn);
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
