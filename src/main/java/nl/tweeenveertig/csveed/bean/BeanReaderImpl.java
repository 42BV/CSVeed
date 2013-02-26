package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.api.BeanReader;
import nl.tweeenveertig.csveed.api.BeanReaderInstructions;
import nl.tweeenveertig.csveed.api.LineReader;
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

    private Reader reader;

    public BeanReaderImpl(Reader reader, Class<T> beanClass) {
        this(reader, new BeanParser<T>().getBeanInstructions(beanClass));
    }

    public BeanReaderImpl(Reader reader, BeanReaderInstructions<T> beanReaderInstructions) {
        this.reader = reader;
        this.beanReaderInstructions = (BeanReaderInstructionsImpl<T>)beanReaderInstructions;
        this.lineReader = new LineReaderImpl(reader, this.beanReaderInstructions.getLineReaderInstructions());
        this.mapper = this.beanReaderInstructions.createMappingStrategy();
        mapper.setBeanReaderInstructions(this.beanReaderInstructions);
        LOG.info("- CSV config / mapping strategy: "+ this.beanReaderInstructions.getMappingStrategy());
    }

    public List<T> read() {
        List<T> beans = new ArrayList<T>();
        while (!isFinished()) {
            T bean = readLine();
            if (bean != null) {
                beans.add(bean);
            }
        }
        return beans;
    }

    public T readLine() {
        Row unmappedRow = lineReader.readLine();
        if (unmappedRow == null) {
            return null;
        }
        mapper.verifyHeader(unmappedRow);
        return mapper.convert(instantiateBean(), unmappedRow, getCurrentLine());
    }

    public int getCurrentLine() {
        return this.lineReader.getCurrentLine();
    }

    public boolean isFinished() {
        return lineReader.isFinished();
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
