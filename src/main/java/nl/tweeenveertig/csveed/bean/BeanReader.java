package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.line.LineReader;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BeanReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(BeanReader.class);

    private LineReader lineReader;

    private BeanReaderInstructionsImpl<T> beanReaderInstructionsImpl;

    private AbstractMappingStrategy<T> strategy;

    public BeanReader(Class<T> beanClass) {
        this(new BeanParser<T>().getBeanInstructions(beanClass));
    }

    public BeanReader(BeanReaderInstructionsImpl<T> beanReaderInstructionsImpl) {
        this.beanReaderInstructionsImpl = beanReaderInstructionsImpl;
        this.lineReader = new LineReader(this.beanReaderInstructionsImpl.getLineReaderInstructions());
        LOG.info("- CSV config / mapping strategy: "+ beanReaderInstructionsImpl.getMappingStrategy());
    }

    public List<T> read(Reader reader) {
        List<T> beans = new ArrayList<T>();
        while (!isFinished()) {
            T bean = readLine(reader);
            if (bean != null) {
                beans.add(bean);
            }
        }
        return beans;
    }

    public T readLine(Reader reader) {
        Row unmappedRow = readLineUnmapped(reader);
        if (unmappedRow == null) {
            return null;
        }
        return getStrategy(unmappedRow).convert(instantiateBean(), unmappedRow, getCurrentLine());
    }

    protected AbstractMappingStrategy<T> getStrategy(Row unmappedRow) {
        if (strategy == null) {
            strategy = beanReaderInstructionsImpl.createMappingStrategy();
            strategy.instruct(beanReaderInstructionsImpl, unmappedRow);
        }
        return strategy;
    }

    private T instantiateBean() {
        try {
            return this.beanReaderInstructionsImpl.newInstance();
        } catch (Exception err) {
            String errorMessage =
                    "Unable to instantiate the bean class "+this.beanReaderInstructionsImpl.getBeanClass().getName()+
                    ". Does it have a no-arg public constructor?";
            LOG.error(errorMessage);
            throw new CsvException(errorMessage, err);
        }
    }

    public List<Row> readUnmapped(Reader reader) {
        return lineReader.read(reader);
    }

    public Row readLineUnmapped(Reader reader) {
        return lineReader.readLine(reader);
    }

    public int getCurrentLine() {
        return this.lineReader.getCurrentLine();
    }

    public boolean isFinished() {
        return lineReader.isFinished();
    }

}
