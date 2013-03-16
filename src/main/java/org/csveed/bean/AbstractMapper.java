package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.bean.conversion.BeanWrapper;
import org.csveed.bean.conversion.ConversionException;
import org.csveed.bean.conversion.DefaultConverters;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;
import org.csveed.util.ExcelColumn;

import java.util.Set;

public abstract class AbstractMapper<T, K> {

    protected BeanReaderInstructionsImpl beanReaderInstructions;

    private boolean verified = false;

    private DefaultConverters defaultConverters = new DefaultConverters();

    public abstract BeanProperty getBeanProperty(Row row, ExcelColumn currentColumn);

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

        ExcelColumn currentColumn = new ExcelColumn();
        for (String cell : row) {
            BeanProperty beanProperty = getBeanProperty(row, currentColumn);
            if (beanProperty == null) {
                currentColumn = currentColumn.nextColumn();
                continue;
            }
            if (beanProperty.isRequired() && (cell == null || cell.equals(""))) {
                throw new CsvException(
                        new RowError("Bean property \""+beanProperty.getPropertyName()+
                                "\" is required and may not be empty or null",
                        row.reportOnColumn(currentColumn.getColumnIndex()), lineNumber));
            }
            try {
                beanWrapper.setProperty(beanProperty, cell);
            } catch (ConversionException err) {
                String message =
                        err.getMessage()+" cell "+getColumnIdentifier(beanProperty)+" ["+cell+"] to "+
                        beanProperty.getPropertyName() + ": " + err.getTypeDescription();
                throw new CsvException(new RowError(message, row.reportOnColumn(currentColumn.getColumnIndex()), lineNumber));
            }
            currentColumn = currentColumn.nextColumn();
        }
        return bean;
    }

    public void setBeanReaderInstructions(BeanReaderInstructionsImpl beanReaderInstructions) {
        this.beanReaderInstructions = beanReaderInstructions;
    }

}
