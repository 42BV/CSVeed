package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.annotations.MappingStrategy;
import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanParser;
import nl.tweeenveertig.csveed.csv.structure.CsvHeader;
import nl.tweeenveertig.csveed.csv.parser.RowReader;
import nl.tweeenveertig.csveed.csv.structure.Row;
import nl.tweeenveertig.csveed.report.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader<T> {

    public static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    private RowReader rowReader;

    private BeanInstructions<T> beanInstructions;

    private CsvHeader header;

    private AbstractMappingStrategy<T> strategy;

    public CsvReader(Class<T> beanClass) {
        this(new BeanParser<T>(beanClass).getBeanInstructions());
    }

    public CsvReader(BeanInstructions<T> beanInstructions) {
        this.beanInstructions = beanInstructions;
        this.rowReader = new RowReader();
        this.rowReader.setSymbolMapping(beanInstructions.getSymbolMapping());
        this.rowReader.getSymbolMapping().logSettings();
        this.rowReader.setStartLine(beanInstructions.getStartRow());
        LOG.info("- CSV config / start line: "+beanInstructions.getStartRow());

        if (this.beanInstructions.isUseHeader()) {
            this.rowReader.setHeaderLine(beanInstructions.getStartRow());
            LOG.info("- CSV config / has structure line? yes");
        } else {
            this.rowReader.setHeaderLine(-1);
            LOG.info("- CSV config / has structure line? no");
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
        Row unmappedLine = readLineUnmapped(reader);
        if (unmappedLine == null) {
            return null;
        }
        if (rowReader.isHeaderLine()) {
            header = new CsvHeader(unmappedLine);
        } else {
            if (strategy == null) {
                strategy = createStrategy(beanInstructions, header, unmappedLine);
            }
            return strategy.convert(this.beanInstructions.newInstance(), unmappedLine, getCurrentLine());
        }
        return null;
    }

    protected AbstractMappingStrategy<T> createStrategy(BeanInstructions<T> beanInstructions, CsvHeader header, Row row) {
        if (beanInstructions.getMappingStrategy() == MappingStrategy.COLUMN_INDEX) {
            return new ColumnIndexStrategy<T>(beanInstructions, header, row);
        } else if (beanInstructions.getMappingStrategy() == MappingStrategy.NAME_MATCHING) {
            return new NameMatchingStrategy<T>(beanInstructions, header);
        }
        return null;
    }

    public List<Row> readUnmapped(Reader reader) {
        return rowReader.readAllLines(reader);
    }

    public Row readLineUnmapped(Reader reader) {
        return rowReader.readLine(reader);
    }

    public int getCurrentLine() {
        return this.rowReader.getCurrentLine();
    }

    public boolean isFinished() {
        return rowReader.isFinished();
    }

}
