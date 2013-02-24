package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Set;

public abstract class AbstractMapper<T, K extends Object> {

    public static final Logger LOG = LoggerFactory.getLogger(AbstractMapper.class);

    protected BeanReaderInstructionsImpl<T> beanReaderInstructions;

    private boolean verified = false;

    public abstract BeanProperty getBeanProperty(Row row, int columnIndex);

    protected abstract Set<K> keys();

    protected abstract void checkKey(Row row, K key);

    public void verifyHeader(Row row) {
        if (verified) {
            return;
        }
        for (K key : keys()) {
            checkKey(row, key);
        }
        verified = true;
    }

    public T convert(T bean, Row row, int lineNumber) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = getBeanProperty(row, indexColumn);
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

    public void setBeanReaderInstructions(BeanReaderInstructionsImpl<T> beanReaderInstructions) {
        this.beanReaderInstructions = beanReaderInstructions;
    }

}
