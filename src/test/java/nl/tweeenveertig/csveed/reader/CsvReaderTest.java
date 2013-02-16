package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.testclasses.BeanWithMultipleStrings;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class CsvReaderTest {

    @Test
    public void getBeans() {
        Reader reader = new StringReader(
            "alpha;beta;gamma\n"+
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
            "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
            "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\""
        );
        CsvReader csvReader = new CsvReader<BeanWithMultipleStrings>(BeanWithMultipleStrings.class);
        List<BeanWithMultipleStrings> beans = csvReader.read(reader);
    }
}
