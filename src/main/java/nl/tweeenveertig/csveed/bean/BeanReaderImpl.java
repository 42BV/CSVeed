package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.line.LineReader;
import nl.tweeenveertig.csveed.api.Row;
import nl.tweeenveertig.csveed.line.LineReaderImpl;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BeanReaderImpl<T> implements BeanReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(BeanReaderImpl.class);

    private LineReader lineReader;

    private BeanReaderInstructionsImpl<T> beanReaderInstructions;

    private AbstractMapper<T, Object> mapper;

    public BeanReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser<T>().getBeanInstructions(beanClass));
    }

    public BeanReaderImpl(Reader reader, BeanReaderInstructions<T> beanReaderInstructions) {
        this.beanReaderInstructions = (BeanReaderInstructionsImpl<T>)beanReaderInstructions;
        this.lineReader = new LineReaderImpl(reader, this.beanReaderInstructions.getLineReaderInstructions());
    }

    public AbstractMapper<T, Object> getMapper() {
        if (this.mapper == null) {
            this.mapper = this.beanReaderInstructions.createMappingStrategy();
            mapper.setBeanReaderInstructions(this.beanReaderInstructions);
            LOG.info("- CSV config / mapping strategy: "+ this.beanReaderInstructions.getMappingStrategy());
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
        Row unmappedRow = lineReader.readLine();
        if (unmappedRow == null) {
            return null;
        }
        getMapper().verifyHeader(unmappedRow);
        return getMapper().convert(instantiateBean(), unmappedRow, getCurrentLine());
    }

    public int getCurrentLine() {
        return this.lineReader.getCurrentLine();
    }

    public boolean isFinished() {
        return lineReader.isFinished();
    }

    @Override
    public LineReader getLineReader() {
        return this.lineReader;
    }

    private T instantiateBean() {
        try {
            return this.beanReaderInstructions.newInstance();
        } catch (Exception err) {
            String errorMessage =
                    "Unable to instantiate the bean class "+this.beanReaderInstructions.getBeanClass().getName()+
                    ". Does it have a no-arg public constructor?";
            LOG.error(errorMessage);
            throw new CsvException(errorMessage, err);
        }
    }

}
