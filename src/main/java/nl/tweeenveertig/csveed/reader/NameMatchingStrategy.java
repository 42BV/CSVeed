package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.header.CsvHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameMatchingStrategy<T> extends AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(NameMatchingStrategy.class);

    public NameMatchingStrategy(BeanInstructions beanInstructions, CsvHeader header) {

        int indexColumn = 0;
        for (String headerColumn : header.getHeader()) {
            BeanProperty beanProperty = beanInstructions.getBeanPropertyWithName(headerColumn);
            if (beanProperty == null) {
                LOG.info("Column index "+indexColumn+ ": ["+headerColumn+"] -> no match");
                continue;
            }
            indexToProperty.put(indexColumn, beanProperty);
            LOG.info("Column index "+indexColumn+": ["+headerColumn+"] to ["+beanProperty.getName()+"]");
            indexColumn++;
        }
    }

}
