package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.testclasses.BeanWithMultipleStrings;
import nl.tweeenveertig.csveed.testclasses.BeanWithVariousTypes;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CsvReaderTest {

    @Test
    public void getBeans() {
        Reader reader = new StringReader(
            "alpha;beta;gamma\n"+
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
            "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
            "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\""
        );
        CsvReader<BeanWithMultipleStrings> csvReader = new CsvReader<BeanWithMultipleStrings>(BeanWithMultipleStrings.class);
        List<BeanWithMultipleStrings> beans = csvReader.read(reader);
        assertEquals(3, beans.size());
        BeanWithMultipleStrings bean = beans.get(0);
        assertEquals("row 1, cell 1", bean.getGamma());
        assertEquals("row 1, cell 2", bean.getBeta());
        assertEquals("row 1, cell 3", bean.getAlpha());
    }

    @Test
    public void variousDataTypes() {
        Reader reader = new StringReader(
            "text;year;number;date;year and month\n"+
            "\"a bit of text\";1984;42.42;1972-01-13;2013-04\n"
        );
        CsvReader<BeanWithVariousTypes> csvReader = new CsvReader<BeanWithVariousTypes>(BeanWithVariousTypes.class);
        List<BeanWithVariousTypes> beans = csvReader.read(reader);
        assertEquals(1, beans.size());
        BeanWithVariousTypes bean = beans.get(0);
        assertEquals("a bit of text", bean.getText());
        assertEquals((Integer)1984, bean.getYear());
        assertEquals(42.42, bean.getNumber());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1972-01-13", formatter.format(bean.getDate()));
        formatter = new SimpleDateFormat("yyyy-MM");
        assertEquals("2013-04", formatter.format(bean.getYearMonth()));
    }
}
