package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.bean.conversion.BeanWrapper;
import nl.tweeenveertig.csveed.bean.conversion.DefaultConverters;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.report.RowError;

import java.util.Set;

public abstract class AbstractMapper<T, K> {

    protected BeanReaderInstructionsImpl beanReaderInstructions;

    private boolean verified = false;

    private DefaultConverters defaultConverters = new DefaultConverters();

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

    public abstract String getColumnIdentifier(BeanProperty beanProperty);

    public T convert(T bean, Row row, int lineNumber) {
        BeanWrapper beanWrapper = new BeanWrapper(defaultConverters, bean);

        int indexColumn = 0;
        for (String cell : row) {
            BeanProperty beanProperty = getBeanProperty(row, indexColumn);
            if (beanProperty == null) {
                indexColumn++;
                continue;
            }
            if (beanProperty.isRequired() && (cell == null || cell.equals(""))) {
                throw new CsvException(
                        new RowError("Bean property \""+beanProperty.getPropertyName()+
                                "\" is required and may not be empty or null",
                        row.reportOnColumn(indexColumn), lineNumber));
            }
            try {
                beanWrapper.setProperty(beanProperty, cell);
            } catch (Exception err) {
                String message =
                        "Problem converting cell "+getColumnIdentifier(beanProperty)+" ["+cell+"] to "+
                        beanProperty.getPropertyType()+" "+beanProperty.getPropertyName();
                throw new CsvException(new RowError(message, row.reportOnColumn(indexColumn), lineNumber));
            }
            indexColumn++;
        }
        return bean;
    }

    public void setBeanReaderInstructions(BeanReaderInstructionsImpl beanReaderInstructions) {
        this.beanReaderInstructions = beanReaderInstructions;
    }

}
