package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.csv.parser.SymbolMapping;
import nl.tweeenveertig.csveed.reader.AbstractMappingStrategy;
import nl.tweeenveertig.csveed.reader.ColumnIndexStrategy;
import nl.tweeenveertig.csveed.report.CsvException;

import java.util.ArrayList;
import java.util.List;

public class Instructions {

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private int startRow = 0;

    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    public Instructions setSymbolMapping(SymbolMapping symbolMapping) {
        this.symbolMapping = symbolMapping;
        return this;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public Instructions setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    public Instructions setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

}
