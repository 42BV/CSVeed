package nl.tweeenveertig.csveed.reader;

import nl.tweeenveertig.csveed.csv.structure.RowReport;
import nl.tweeenveertig.csveed.report.CsvException;
import nl.tweeenveertig.csveed.testclasses.*;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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
    public void tabSeparated() {
        Reader reader = new StringReader(
            "alpha;beta;gamma\n"+
            "'\\'row\\' 1, cell 1'\t'row 1, cell 2'\t'row 1, cell 3'\r"+
            "'\\'row\\' 2, cell 1'\t'row 2, cell 2'\t'row 2, cell 3'\r"+
            "'\\'row\\' 3, cell 1'\t'row 3, cell 2'\t'row 3, cell 3'"
        );
        CsvReader<BeanWithAlienSettings> csvReader = new CsvReader<BeanWithAlienSettings>(BeanWithAlienSettings.class);
        List<BeanWithAlienSettings> beans = csvReader.read(reader);
        assertEquals(3, beans.size());
        BeanWithAlienSettings bean = beans.get(0);
        assertEquals("'row' 1, cell 1", bean.getGamma());
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

    @Test
    public void noHeader() {
        Reader reader = new StringReader(
            "\"a bit of text\";1984;42.42;1972-01-13;2013-04\n"
        );
        CsvReader<BeanWithoutHeader> csvReader = new CsvReader<BeanWithoutHeader>(BeanWithoutHeader.class);
        List<BeanWithoutHeader> beans = csvReader.read(reader);
        assertEquals(1, beans.size());
    }
    
    @Test
    public void nameMatching() {
        Reader reader = new StringReader(
            "street;city;postal code;ignore this\n"+
            "\"Some street\";\"Some city\";\"Some postal code\";\"Some ignoring\""
        );
        CsvReader<BeanWithNameMatching> csvReader = new CsvReader<BeanWithNameMatching>(BeanWithNameMatching.class);
        List<BeanWithNameMatching> beans = csvReader.read(reader);
        assertEquals(1, beans.size());
        BeanWithNameMatching bean = beans.get(0);
        assertEquals("Some street", bean.getLine1());
        assertEquals("Some city", bean.getLine2());
        assertEquals("Some postal code", bean.getLine3());
    }

    @Test
    public void indexMatching() {
        Reader reader = new StringReader(
            "\"line-1\";\"line0\";\"line1\";\"line2\";\"line3\""
        );
        CsvReader<BeanWithCustomIndexes> csvReader = new CsvReader<BeanWithCustomIndexes>(BeanWithCustomIndexes.class);
        BeanWithCustomIndexes bean = csvReader.readLine(reader);
        assertEquals("line0", bean.getLine0());
        assertEquals("line1", bean.getLine1());
        assertEquals("line2", bean.getLine2());
        assertEquals("line3", bean.getLine3());
    }
    
    @Test
    public void numberOfIgnores() {
        Reader reader = new StringReader(
            "14;28;42"
        );
        CsvReader<BeanLotsOfIgnores> csvReader = new CsvReader<BeanLotsOfIgnores>(BeanLotsOfIgnores.class);
        BeanLotsOfIgnores bean = csvReader.readLine(reader);
        assertEquals((Integer)14, bean.getTakeThis1());
        assertEquals((Integer)28, bean.getPickThis1());
        assertEquals((Integer)42, bean.getChooseThis1());
        assertNull(bean.getDitchThat1());
        assertNull(bean.getLeaveThat1());
    }

    @Test(expected = CsvException.class)
    public void illegalToken() {
        Reader reader = new StringReader(
            "\"alpha\";\"beta\";\"gamma\"a\n"
        );
        CsvReader<BeanSimple> csvReader = new CsvReader<BeanSimple>(BeanSimple.class);
        csvReader.read(reader);
    }

}
