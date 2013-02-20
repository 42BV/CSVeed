package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.structure.CsvHeader;
import nl.tweeenveertig.csveed.csv.structure.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractMappingStrategy<T> {

    public static final Logger LOG = LoggerFactory.getLogger(AbstractMappingStrategy.class);

    protected Map<Integer, BeanProperty> indexToProperty = new TreeMap<Integer, BeanProperty>();

    public abstract void instruct(BeanInstructions beanInstructions, CsvHeader header, Row row);

    public BeanProperty getBeanProperty(int indexColumn) {
        return indexToProperty.get(indexColumn);
    }

    public T convert(T bean, Row row, int lineNumber) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = getBeanProperty(indexColumn);
            if (beanProperty == null) {
                indexColumn++;
                continue;
            }
            if (beanProperty.getConverter() != null) {
                beanWrapper.registerCustomEditor(
                        beanProperty.getPropertyDescriptor().getPropertyType(),
                        beanProperty.getPropertyDescriptor().getName(), // ascertain the converter is only used on this property
                        beanProperty.getConverter());
            }
            try {
                beanWrapper.setPropertyValue(beanProperty.getPropertyDescriptor().getName(), cell);
            } catch (Exception err) {
                LOG.error(err.getMessage());
                String errorMessage = "Problem converting ["+cell+"] to "+beanProperty.getPropertyDescriptor().getPropertyType().getName();
                LOG.error(errorMessage);
                for (String line : row.reportOnColumn(indexColumn).getPrintableLines()) {
                    LOG.error(lineNumber+": "+line);
                }
                throw new CsvException(errorMessage, err, row.reportOnColumn(indexColumn), lineNumber);
            }
            indexColumn++;
        }
        return bean;
    }

}
