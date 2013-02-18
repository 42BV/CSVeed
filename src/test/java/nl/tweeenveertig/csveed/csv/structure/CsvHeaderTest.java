package nl.tweeenveertig.csveed.csv.structure;

import nl.tweeenveertig.csveed.reader.CsvReader;
import nl.tweeenveertig.csveed.test.model.BeanSimple;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CsvHeaderTest {

    @Test
    public void checkNumberOfColumns() {
        CsvHeader header = new CsvHeader(getRow("alpha;beta;gamma"));
        assertFalse(header.checkLine(getRow("1;2;3;4")));
        assertTrue(header.checkLine(getRow("1;2;3")));
    }

    public Row getRow(String row) {
        Reader reader = new StringReader(row);
        CsvReader<BeanSimple> csvReader = new CsvReader<BeanSimple>(BeanSimple.class);
        return csvReader.readLineUnmapped(reader);
    }
}
