package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.bean.instructions.BeanInstructions;
import nl.tweeenveertig.csveed.bean.instructions.BeanParser;
import nl.tweeenveertig.csveed.bean.instructions.BeanProperty;
import nl.tweeenveertig.csveed.csv.header.CsvHeader;
import nl.tweeenveertig.csveed.csv.parser.LineReader;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader<T> {

    private LineReader lineReader;

    private BeanInstructions<T> beanInstructions;

    private CsvHeader header;

    public CsvReader(Class<T> beanClass) {
        this(new BeanParser<T>(beanClass).getBeanInstructions());
    }

    public CsvReader(BeanInstructions<T> beanInstructions) {
        this.beanInstructions = beanInstructions;
        this.lineReader = new LineReader();
        this.lineReader.setSymbolMapping(beanInstructions.getSymbolMapping());
        this.lineReader.setStartLine(beanInstructions.getStartRow());
        if (this.beanInstructions.isUseHeader()) {
            this.lineReader.setHeaderLine(beanInstructions.getStartRow());
        } else {
            this.lineReader.setHeaderLine(-1);
        }
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
            return mapLineToBean(unmappedLine);
        }
        return null;
    }

    public T mapLineToBean(List<String> line) {
        T bean = this.beanInstructions.newInstance();
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);

        int indexColumn = 0;
        for (String cell : line) {
            BeanProperty beanProperty = beanInstructions.getBeanPropertyWithIndex(indexColumn);
            if (beanProperty == null) {
                // error condition
                continue;
            }
            beanWrapper.setPropertyValue(beanProperty.getPropertyDescriptor().getName(), cell);
            indexColumn++;
        }
        return bean;
    }

    public List<List<String>> readUnmapped(Reader reader) {
        return lineReader.readAllLines(reader);
    }

    public List<String> readLineUnmapped(Reader reader) {
        List<String> unmappedLine = lineReader.readLine(reader);
        return unmappedLine;
    }

    public int getCurrentLine() {
        return this.lineReader.getCurrentLine();
    }

    public boolean isFinished() {
        return lineReader.isFinished();
    }

}
