package org.csveed.bean;

import org.csveed.api.Row;
import org.csveed.bean.conversion.BeanWrapper;
import org.csveed.bean.conversion.ConversionException;
import org.csveed.bean.conversion.DefaultConverters;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;

import java.util.Set;

public abstract class AbstractMapper<T> {

    protected BeanReaderInstructionsImpl beanReaderInstructions;

    private boolean verified = false;

    private DefaultConverters defaultConverters = new DefaultConverters();

    public abstract BeanProperty getBeanProperty(Column currentColumn);

    protected abstract Set<Column> keys();

    protected abstract void checkKey(Row row, Column key);

    public void verifyHeader(Row row) {
        if (verified) {
            return;
        }
        for (Column key : keys()) {
            checkKey(row, key);
        }
        verified = true;
    }

    protected abstract Column getColumn(Row row);

    public T convert(T bean, Row row, int lineNumber) {
        BeanWrapper beanWrapper = new BeanWrapper(defaultConverters, bean);

        Column currentColumn = null;
        for (String cell : row) {
            currentColumn = currentColumn == null ? getColumn(row) : currentColumn.nextColumn();
            BeanProperty beanProperty = getBeanProperty(currentColumn);
            if (beanProperty == null) {
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
                        err.getMessage()+" cell"+currentColumn.getColumnText()+" ["+cell+"] to "+
                        beanProperty.getPropertyName() + ": " + err.getTypeDescription();
                throw new CsvException(new RowError(message, row.reportOnColumn(currentColumn.getColumnIndex()), lineNumber));
            }
        }
        return bean;
    }

    public void setBeanReaderInstructions(BeanReaderInstructionsImpl beanReaderInstructions) {
        this.beanReaderInstructions = beanReaderInstructions;
    }

}
