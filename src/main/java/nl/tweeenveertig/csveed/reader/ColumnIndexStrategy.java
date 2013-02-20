package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.structure.CsvHeader;
import nl.tweeenveertig.csveed.csv.structure.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnIndexStrategy<T> extends AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnIndexStrategy.class);

    public void instruct(BeanInstructions beanInstructions, CsvHeader header, Row row) {

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = beanInstructions.getBeanPropertyWithIndex(indexColumn);
            if (beanProperty == null) {
                LOG.info("Column index "+indexColumn+": no match");
                indexColumn++;
                continue;
            }
            indexToProperty.put(indexColumn, beanProperty);
            String headerColumn = null;
            if (header != null) {
                headerColumn = header.getName(indexColumn);
            }
            LOG.info("Column index "+indexColumn+": ["+(headerColumn == null ? "" : headerColumn + "] to [")+beanProperty.getName()+"]");
            indexColumn++;
        }
    }

}
