package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.header.CsvHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ColumnIndexStrategy<T> extends AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(ColumnIndexStrategy.class);

    public ColumnIndexStrategy(BeanInstructions beanInstructions, CsvHeader header, List<String> line) {

        int indexColumn = 0;
        for (String cell : line) {
            BeanProperty beanProperty = beanInstructions.getBeanPropertyWithIndex(indexColumn);
            if (beanProperty == null) {
                LOG.info("Column index "+indexColumn+": no match");
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
