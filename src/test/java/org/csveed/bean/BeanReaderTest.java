package org.csveed.bean;

import org.csveed.report.CsvException;
import org.csveed.test.model.*;
import org.csveed.token.ParseState;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class BeanReaderTest {

    @Test
    public void convertToEnum() {
        Reader reader = new StringReader(
                "parseState\n"+
                "\"FIRST_CHAR_INSIDE_QUOTED_FIELD\"");
        BeanReader<BeanWithEnum> beanReader = new BeanReaderImpl<BeanWithEnum>(reader, BeanWithEnum.class);
        BeanWithEnum bean = beanReader.readBean();
        assertEquals(ParseState.FIRST_CHAR_INSIDE_QUOTED_FIELD, bean.getParseState());
    }

    @Test(expected = CsvException.class)
    public void missingConverter() {
        Reader reader = new StringReader(
            "alpha\n"+
            "\"row 1, cell 1\""
        );
        BeanReader<BeanWithNonStandardObject> beanReader = new BeanReaderImpl<BeanWithNonStandardObject>(reader, BeanWithNonStandardObject.class);
        beanReader.readBean();
    }

    @Test (expected = CsvException.class)
    public void illegalColumnIndexMappingTooLow() {
        checkIllegalMapping(
                new BeanReaderInstructionsImpl(BeanWithMultipleStrings.class)
                        .setMapper(ColumnIndexMapper.class)
                        .mapColumnIndexToProperty(-1, "alpha")
        );
    }

    @Test (expected = CsvException.class)
    public void illegalColumnIndexMappingTooHigh() {
        checkIllegalMapping(
            new BeanReaderInstructionsImpl(BeanWithMultipleStrings.class)
                .setMapper(ColumnIndexMapper.class)
                .mapColumnIndexToProperty(99, "alpha")
        );
    }

    @Test (expected = CsvException.class)
    public void illegalColumnName() {
        checkIllegalMapping(
            new BeanReaderInstructionsImpl(BeanWithMultipleStrings.class)
                .setMapper(ColumnNameMapper.class)
                .mapColumnNameToProperty("Alphabetical", "alpha")
        );
    }

    protected void checkIllegalMapping(BeanReaderInstructions beanReaderInstructions) {
        Reader reader = new StringReader(
            "alpha;beta;gamma\n"+
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\""
        );
        BeanReader<BeanWithMultipleStrings> beanReader = new BeanReaderImpl<BeanWithMultipleStrings>(reader, beanReaderInstructions);
        beanReader.readBean();
    }

    @Test
    public void customNumberConversion() {
        Reader reader = new StringReader(
            "money\n"+
            "11.398,22"
        );
        BeanReader<BeanWithCustomNumberAnnotated> beanReader = new BeanReaderImpl<BeanWithCustomNumberAnnotated>(reader, BeanWithCustomNumberAnnotated.class);
        BeanWithCustomNumberAnnotated bean = beanReader.readBean();
        assertEquals(11398.22, bean.getNumber());
    }

    @Test
    public void getBeansManualMapping() {
        Reader reader = new StringReader(
            "a;c;b\n"+
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
            "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
            "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\""
        );
        BeanReaderImpl<BeanWithMultipleStrings> beanReader = new BeanReaderImpl<BeanWithMultipleStrings>(
                reader,
                new BeanReaderInstructionsImpl(BeanWithMultipleStrings.class)
                .setMapper(ColumnNameMapper.class)
                .mapColumnNameToProperty("a", "alpha")
                .mapColumnNameToProperty("b", "beta")
                .mapColumnNameToProperty("c", "gamma")
        );
        List<BeanWithMultipleStrings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithMultipleStrings bean = beans.get(0);
        assertEquals("row 1, cell 1", bean.getAlpha());
        assertEquals("row 1, cell 2", bean.getGamma());
        assertEquals("row 1, cell 3", bean.getBeta());
    }

    @Test
    public void getBeans() {
        Reader reader = new StringReader(
            "alpha;beta;gamma\n"+
            "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
            "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"+
            "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\""
        );
        BeanReaderImpl<BeanWithMultipleStrings> beanReader =
                new BeanReaderImpl<BeanWithMultipleStrings>(reader, BeanWithMultipleStrings.class);
        List<BeanWithMultipleStrings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithMultipleStrings bean = beans.get(0);
        assertEquals("row 1, cell 1", bean.getGamma());
        assertEquals("row 1, cell 2", bean.getBeta());
        assertEquals("row 1, cell 3", bean.getAlpha());
    }

    @Test
    public void tabSeparated() {
        Reader reader = new StringReader(
            "alpha\tbeta\tgamma\r"+
            "'\\'row\\' 1, cell 1'\t'row 1, cell 2'\t'row 1, cell 3'\r"+
            "'\\'row\\' 2, cell 1'\t'row 2, cell 2'\t'row 2, cell 3'\r"+
            "'\\'row\\' 3, cell 1'\t'row 3, cell 2'\t'row 3, cell 3'"
        );
        BeanReaderImpl<BeanWithAlienSettings> beanReader =
                new BeanReaderImpl<BeanWithAlienSettings>(reader, BeanWithAlienSettings.class);
        List<BeanWithAlienSettings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithAlienSettings bean = beans.get(0);
        assertEquals("'row' 1, cell 1", bean.getGamma());
        assertEquals("row 1, cell 2", bean.getBeta());
        assertEquals("row 1, cell 3", bean.getAlpha());
    }

    @Test(expected = CsvException.class)
    public void errorInDate() {
        Reader reader = new StringReader(
                "text;year;number;date;year and month\n"+
                "\"a bit of text\";1984;42.42;1972-13-01;2013-04\n" // Month and day in reverse order
        );
        BeanReaderImpl<BeanWithVariousTypes> beanReader =
                new BeanReaderImpl<BeanWithVariousTypes>(reader, BeanWithVariousTypes.class);
        beanReader.readBeans();
    }

    @Test
    public void variousDataTypes() {
        Reader reader = new StringReader(
            "text;year;number;date;year and month\n"+
            "\"a bit of text\";1984;42.42;1972-01-13;2013-04\n"
        );
        BeanReaderImpl<BeanWithVariousTypes> beanReader =
                new BeanReaderImpl<BeanWithVariousTypes>(reader, BeanWithVariousTypes.class);
        List<BeanWithVariousTypes> beans = beanReader.readBeans();
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
        BeanReaderImpl<BeanWithoutHeader> beanReader =
                new BeanReaderImpl<BeanWithoutHeader>(reader, BeanWithoutHeader.class);
        List<BeanWithoutHeader> beans = beanReader.readBeans();
        assertEquals(1, beans.size());
    }
    
    @Test
    public void nameMatching() {
        Reader reader = new StringReader(
            "street;city;postal code;ignore this\n"+
            "\"Some street\";\"Some city\";\"Some postal code\";\"Some ignoring\""
        );
        BeanReaderImpl<BeanWithNameMatching> beanReader = new BeanReaderImpl<BeanWithNameMatching>(reader, BeanWithNameMatching.class);
        List<BeanWithNameMatching> beans = beanReader.readBeans();
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
        BeanReaderImpl<BeanWithCustomIndexes> beanReader = new BeanReaderImpl<BeanWithCustomIndexes>(reader, BeanWithCustomIndexes.class);
        BeanWithCustomIndexes bean = beanReader.readBean();
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
        BeanReaderImpl<BeanLotsOfIgnores> beanReader = new BeanReaderImpl<BeanLotsOfIgnores>(reader, BeanLotsOfIgnores.class);
        BeanLotsOfIgnores bean = beanReader.readBean();
        assertEquals((Integer)14, bean.getTakeThis1());
        assertEquals((Integer)28, bean.getPickThis1());
        assertEquals((Integer)42, bean.getChooseThis1());
        assertNull(bean.getDitchThat1());
        assertNull(bean.getLeaveThat1());
    }

    @Test
    public void customPropertyEditor() {
        Reader reader = new StringReader(
            "\"some text\""
        );
        BeanReaderImpl<BeanWithConverter> beanReader = new BeanReaderImpl<BeanWithConverter>(reader, BeanWithConverter.class);
        BeanWithConverter bean = beanReader.readBean();
        assertEquals("some text", bean.getBean().getName());
    }

    @Test(expected = CsvException.class)
    public void illegalToken() {
        Reader reader = new StringReader(
            "\"alpha\";\"beta\";\"gamma\"a\n"
        );
        BeanReaderImpl<BeanSimple> beanReader = new BeanReaderImpl<BeanSimple>(reader, BeanSimple.class);
        beanReader.readBeans();
    }

    @Test(expected = CsvException.class)
    public void beanMappingError() {
        Reader reader = new StringReader(
            "text;year;number;date;year and month\n"+
            "\"a bit of text\";UNEXPECTED TEXT!!!;42.42;1972-01-13;2013-04\n"
        );
        BeanReaderImpl<BeanWithVariousTypes> beanReader = new BeanReaderImpl<BeanWithVariousTypes>(reader, BeanWithVariousTypes.class);
        beanReader.readBeans();
    }

    @Test(expected = CsvException.class)
    public void cannotConvertToNonStandardObject() {
        Reader reader = new StringReader(
            "\"can I convert this to a simple bean?\""
        );
        BeanReaderImpl<BeanWithNonStandardObject> beanReader = new BeanReaderImpl<BeanWithNonStandardObject>(reader, BeanWithNonStandardObject.class);
        beanReader.readBeans();
    }

    @Test(expected = CsvException.class)
    public void nonInstantiableBean() {
        Reader reader = new StringReader(
            "\"can I convert this to a simple bean?\""
        );
        BeanReaderImpl<BeanWithoutNoArgPublicConstructor> beanReader = new BeanReaderImpl<BeanWithoutNoArgPublicConstructor>(reader, BeanWithoutNoArgPublicConstructor.class);
        beanReader.readBeans();
    }

}
