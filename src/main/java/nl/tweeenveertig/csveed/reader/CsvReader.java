package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.annotations.MappingStrategy;
import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanParser;
import nl.tweeenveertig.csveed.csv.header.CsvHeader;
import nl.tweeenveertig.csveed.csv.parser.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    private LineReader lineReader;

    private BeanInstructions<T> beanInstructions;

    private CsvHeader header;

    private AbstractMappingStrategy<T> strategy;

    public CsvReader(Class<T> beanClass) {
        this(new BeanParser<T>(beanClass).getBeanInstructions());
    }

    public CsvReader(BeanInstructions<T> beanInstructions) {
        this.beanInstructions = beanInstructions;
        this.lineReader = new LineReader();
        this.lineReader.setSymbolMapping(beanInstructions.getSymbolMapping());
        this.lineReader.getSymbolMapping().logSettings();
        this.lineReader.setStartLine(beanInstructions.getStartRow());
        LOG.info("- CSV config / start line: "+beanInstructions.getStartRow());

        if (this.beanInstructions.isUseHeader()) {
            this.lineReader.setHeaderLine(beanInstructions.getStartRow());
            LOG.info("- CSV config / has header line? yes");
        } else {
            this.lineReader.setHeaderLine(-1);
            LOG.info("- CSV config / has header line? no");
        }
        LOG.info("- CSV config / mapping strategy: "+beanInstructions.getMappingStrategy());
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
        List<String> unmappedLine = readLineUnmapped(reader);
        if (unmappedLine == null) {
            return null;
        }
        if (lineReader.isHeaderLine()) {
            header = new CsvHeader(unmappedLine);
        } else {
            if (strategy == null) {
                strategy = createStrategy(beanInstructions, header, unmappedLine);
            }
            return strategy.convert(this.beanInstructions.newInstance(), unmappedLine);
        }
        return null;
    }

    protected AbstractMappingStrategy<T> createStrategy(BeanInstructions<T> beanInstructions, CsvHeader header, List<String> line) {
        if (beanInstructions.getMappingStrategy() == MappingStrategy.COLUMN_INDEX) {
            return new ColumnIndexStrategy<T>(beanInstructions, header, line);
        } else if (beanInstructions.getMappingStrategy() == MappingStrategy.NAME_MATCHING) {
            return new NameMatchingStrategy<T>(beanInstructions, header);
        }
        return null;
    }

    public List<List<String>> readUnmapped(Reader reader) {
        return lineReader.readAllLines(reader);
    }

    public List<String> readLineUnmapped(Reader reader) {
        return lineReader.readLine(reader);
    }

    public int getCurrentLine() {
        return this.lineReader.getCurrentLine();
    }

    public boolean isFinished() {
        return lineReader.isFinished();
    }

}
