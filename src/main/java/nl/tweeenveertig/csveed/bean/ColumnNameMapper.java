package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnNameMapper<T> extends AbstractMapper<T> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnNameMapper.class);

    public void instruct(BeanReaderInstructionsImpl beanReaderInstructions, Row row) {

        int indexColumn = 0;
        for (String headerColumn : row.getHeader()) {
            BeanProperty beanProperty = beanReaderInstructions.getBeanPropertyWithName(headerColumn);
            if (beanProperty == null) {
                LOG.info("Column index "+indexColumn+ ": ["+headerColumn+"] -> no match");
                indexColumn++;
                continue;
            }
            indexToProperty.put(indexColumn, beanProperty);
            LOG.info("Column index "+indexColumn+": ["+headerColumn+"] to ["+beanProperty.getPropertyName()+"]");
            indexColumn++;
        }
    }

}
