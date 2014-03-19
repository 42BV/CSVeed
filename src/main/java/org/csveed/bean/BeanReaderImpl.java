package org.csveed.bean;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.csveed.api.Row;
import org.csveed.report.CsvException;
import org.csveed.report.GeneralError;
import org.csveed.row.HeaderImpl;
import org.csveed.row.RowReader;
import org.csveed.row.RowReaderImpl;

public class BeanReaderImpl<T> implements BeanReader<T> {

    private RowReader rowReader;

    private BeanReaderInstructionsImpl beanReaderInstructions;

    private AbstractMapper<T> mapper;

    private DynamicColumn currentDynamicColumn;

    private Row unmappedRow;

    public BeanReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser().getBeanInstructions(beanClass));
    }

    public BeanReaderImpl(Reader reader, BeanReaderInstructions beanReaderInstructions) {
        this.beanReaderInstructions = (BeanReaderInstructionsImpl) beanReaderInstructions;
        this.rowReader = new RowReaderImpl(reader, this.beanReaderInstructions.getRowReaderInstructions());
        this.currentDynamicColumn = new DynamicColumn(this.beanReaderInstructions.getStartIndexDynamicColumns());
    }

    public AbstractMapper<T> getMapper() {
        if (this.mapper == null) {
            this.mapper = this.createMappingStrategy();
            mapper.setBeanReaderInstructions(this.beanReaderInstructions);
        }
        return mapper;
    }

    public List<T> readBeans() {
        List<T> beans = new ArrayList<T>();
        while (!isFinished()) {
            T bean = readBean();
            if (bean != null) {
                beans.add(bean);
            }
        }
        return beans;
    }

    public T readBean() {
        logSettings();

        if (this.beanReaderInstructions.useHeader())
            getMapper().verifyHeader(getHeader());

        currentDynamicColumn.checkForReset(((RowReaderImpl) rowReader).getMaxNumberOfColumns());
        if (currentDynamicColumn.atFirstDynamicColumn()) {
            unmappedRow = rowReader.readRow();
        }
        if (unmappedRow == null) {
            return null;
        }

        T bean = getMapper().convert(instantiateBean(), unmappedRow, getCurrentLine(), currentDynamicColumn);
        currentDynamicColumn.advanceDynamicColumn();
        return bean;
    }

    protected void logSettings() {
        this.beanReaderInstructions.logSettings();
    }

    protected HeaderImpl getHeader() {
        if (this.beanReaderInstructions.useHeader())
            return rowReader.getHeader();
        return null;
    }

    @Override
    public HeaderImpl readHeader() {
        return rowReader.readHeader();
    }

    public int getCurrentLine() {
        return this.rowReader.getCurrentLine();
    }

    public boolean isFinished() {
        return rowReader.isFinished();
    }

    @Override
    public RowReader getRowReader() {
        return this.rowReader;
    }

    private T instantiateBean() {
        try {
            return this.getBeanClass().newInstance();
        } catch (Exception err) {
            throw new CsvException(new GeneralError(
                    "Unable to instantiate the bean class " + this.getBeanClass().getName() +
                            ". Does it have a no-arg public constructor?"
                    ));
        }
    }

    @SuppressWarnings("unchecked")
    public Class<T> getBeanClass() {
        return this.beanReaderInstructions.getBeanClass();
    }

    @SuppressWarnings("unchecked")
    public AbstractMapper<T> createMappingStrategy() {
        try {
            return this.beanReaderInstructions.getMappingStrategy().newInstance();
        } catch (Exception err) {
            throw new CsvException(new GeneralError(
                    "Unable to instantiate the mapping strategy"
                    ));
        }
    }

    public BeanReaderInstructions getBeanReaderInstructions() {
        return this.beanReaderInstructions;
    }

}
