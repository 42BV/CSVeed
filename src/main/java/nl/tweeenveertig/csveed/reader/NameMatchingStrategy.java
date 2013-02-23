package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.structure.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameMatchingStrategy<T> extends AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(NameMatchingStrategy.class);

    public void instruct(BeanInstructions beanInstructions, Row row) {

        int indexColumn = 0;
        for (String headerColumn : row.getHeader()) {
            BeanProperty beanProperty = beanInstructions.getBeanPropertyWithName(headerColumn);
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
