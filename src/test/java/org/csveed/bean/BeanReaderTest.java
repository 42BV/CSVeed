/*
 * CSVeed (https://github.com/42BV/CSVeed)
 *
 * Copyright 2013-2023 CSVeed.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of The Apache Software License,
 * Version 2.0 which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0.txt
 */
package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

import org.csveed.report.CsvException;
import org.csveed.test.model.BeanCommodity;
import org.csveed.test.model.BeanLotsOfIgnores;
import org.csveed.test.model.BeanSimple;
import org.csveed.test.model.BeanWithAlienSettings;
import org.csveed.test.model.BeanWithConverter;
import org.csveed.test.model.BeanWithCustomIndexes;
import org.csveed.test.model.BeanWithCustomNumberAnnotated;
import org.csveed.test.model.BeanWithEnum;
import org.csveed.test.model.BeanWithEnumAndMore;
import org.csveed.test.model.BeanWithMultipleStrings;
import org.csveed.test.model.BeanWithNameMatching;
import org.csveed.test.model.BeanWithNonStandardObject;
import org.csveed.test.model.BeanWithVariousTypes;
import org.csveed.test.model.BeanWithoutHeader;
import org.csveed.test.model.BeanWithoutNoArgPublicConstructor;
import org.csveed.token.ParseState;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanReaderTest.
 */
public class BeanReaderTest {

    /**
     * Dynamic columns.
     */
    @Test
    public void dynamicColumns() {
        Reader reader = new StringReader(
                "commodity;language;14-01;14-02;14-03\n" + "corn;NL;1;2;3\n" + "corn;BE;4;5;6\n");
        BeanReader<BeanCommodity> beanReader = new BeanReaderImpl<>(reader, BeanCommodity.class);
        List<BeanCommodity> commodities = beanReader.readBeans();
        assertEquals(6, commodities.size());
        assertBeanCommodity(commodities.get(0), "corn", "NL", "14-01", 1);
        assertBeanCommodity(commodities.get(1), "corn", "NL", "14-02", 2);
        assertBeanCommodity(commodities.get(2), "corn", "NL", "14-03", 3);
        assertBeanCommodity(commodities.get(3), "corn", "BE", "14-01", 4);
        assertBeanCommodity(commodities.get(4), "corn", "BE", "14-02", 5);
        assertBeanCommodity(commodities.get(5), "corn", "BE", "14-03", 6);
    }

    /**
     * Assert bean commodity.
     *
     * @param beanCommodity
     *            the bean commodity
     * @param expectedCommodity
     *            the expected commodity
     * @param expectedLanguage
     *            the expected language
     * @param expectedDay
     *            the expected day
     * @param expectedAmount
     *            the expected amount
     */
    protected void assertBeanCommodity(BeanCommodity beanCommodity, String expectedCommodity, String expectedLanguage,
            String expectedDay, int expectedAmount) {
        assertEquals(expectedCommodity, beanCommodity.getCommodity());
        assertEquals(expectedLanguage, beanCommodity.getLanguage());
        assertEquals(expectedDay, beanCommodity.getDay());
        assertEquals(expectedAmount, beanCommodity.getAmount());
    }

    /**
     * Enum may be null.
     */
    @Test
    public void enumMayBeNull() {
        Reader reader = new StringReader(
                "name;parseState\n" + "alpha;\"FIRST_CHAR_INSIDE_QUOTED_FIELD\"\n" + "beta;\n");
        BeanReader<BeanWithEnumAndMore> beanReader = new BeanReaderImpl<>(reader, BeanWithEnumAndMore.class);
        List<BeanWithEnumAndMore> beans = beanReader.readBeans();
        assertEquals(2, beans.size());
        assertEquals(null, beans.get(1).getParseState());
    }

    /**
     * Convert to enum.
     */
    @Test
    public void convertToEnum() {
        Reader reader = new StringReader("parseState\n" + "\"FIRST_CHAR_INSIDE_QUOTED_FIELD\"");
        BeanReader<BeanWithEnum> beanReader = new BeanReaderImpl<>(reader, BeanWithEnum.class);
        BeanWithEnum bean = beanReader.readBean();
        assertEquals(ParseState.FIRST_CHAR_INSIDE_QUOTED_FIELD, bean.getParseState());
    }

    /**
     * Missing converter.
     */
    @Test
    public void missingConverter() {
        Reader reader = new StringReader("alpha\n" + "\"row 1, cell 1\"");
        BeanReader<BeanWithNonStandardObject> beanReader = new BeanReaderImpl<>(reader,
                BeanWithNonStandardObject.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBean();
        });
    }

    /**
     * Illegal column index mapping too low.
     */
    @Test
    public void illegalColumnIndexMappingTooLow() {
        assertThrows(CsvException.class, () -> {
            new BeanInstructionsImpl(BeanWithMultipleStrings.class).setMapper(ColumnIndexMapper.class)
                    .mapColumnIndexToProperty(-1, "alpha");
        });
    }

    /**
     * Illegal column index mapping too high.
     */
    @Test
    public void illegalColumnIndexMappingTooHigh() {
        checkIllegalMapping(new BeanInstructionsImpl(BeanWithMultipleStrings.class).setMapper(ColumnIndexMapper.class)
                .mapColumnIndexToProperty(99, "alpha"));
    }

    /**
     * Illegal column name.
     */
    @Test
    public void illegalColumnName() {
        checkIllegalMapping(new BeanInstructionsImpl(BeanWithMultipleStrings.class).setMapper(ColumnNameMapper.class)
                .mapColumnNameToProperty("Alphabetical", "alpha"));
    }

    /**
     * Check illegal mapping.
     *
     * @param beanInstructions
     *            the bean instructions
     */
    protected void checkIllegalMapping(BeanInstructions beanInstructions) {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"");
        BeanReader<BeanWithMultipleStrings> beanReader = new BeanReaderImpl<>(reader, beanInstructions);
        assertThrows(CsvException.class, () -> {
            beanReader.readBean();
        });
    }

    /**
     * Custom number conversion.
     */
    @Test
    public void customNumberConversion() {
        Reader reader = new StringReader("money\n" + "11.398,22");
        BeanReader<BeanWithCustomNumberAnnotated> beanReader = new BeanReaderImpl<>(reader,
                BeanWithCustomNumberAnnotated.class);
        BeanWithCustomNumberAnnotated bean = beanReader.readBean();
        assertEquals(Double.valueOf(11398.22), bean.getNumber());
    }

    /**
     * Gets the beans manual mapping.
     */
    @Test
    public void getBeansManualMapping() {
        Reader reader = new StringReader("a;c;b\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
                + "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"");
        BeanReader<BeanWithMultipleStrings> beanReader = new BeanReaderImpl<>(reader,
                new BeanInstructionsImpl(BeanWithMultipleStrings.class).setMapper(ColumnNameMapper.class)
                        .mapColumnNameToProperty("a", "alpha").mapColumnNameToProperty("b", "beta")
                        .mapColumnNameToProperty("c", "gamma"));
        List<BeanWithMultipleStrings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithMultipleStrings bean = beans.get(0);
        assertEquals("row 1, cell 1", bean.getAlpha());
        assertEquals("row 1, cell 2", bean.getGamma());
        assertEquals("row 1, cell 3", bean.getBeta());
    }

    /**
     * Gets the beans.
     */
    @Test
    public void getBeans() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"
                        + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
                        + "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"");
        BeanReader<BeanWithMultipleStrings> beanReader = new BeanReaderImpl<>(reader, BeanWithMultipleStrings.class);
        List<BeanWithMultipleStrings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithMultipleStrings bean = beans.get(0);
        assertEquals("row 1, cell 1", bean.getGamma());
        assertEquals("row 1, cell 2", bean.getBeta());
        assertEquals("row 1, cell 3", bean.getAlpha());
    }

    /**
     * Tab separated.
     */
    @Test
    public void tabSeparated() {
        Reader reader = new StringReader(
                "alpha\tbeta\tgamma\r" + "'\\'row\\' 1, cell 1'\t'row 1, cell 2'\t'row 1, cell 3'\r"
                        + "'\\'row\\' 2, cell 1'\t'row 2, cell 2'\t'row 2, cell 3'\r"
                        + "'\\'row\\' 3, cell 1'\t'row 3, cell 2'\t'row 3, cell 3'");
        BeanReader<BeanWithAlienSettings> beanReader = new BeanReaderImpl<>(reader, BeanWithAlienSettings.class);
        List<BeanWithAlienSettings> beans = beanReader.readBeans();
        assertEquals(3, beans.size());
        BeanWithAlienSettings bean = beans.get(0);
        assertEquals("'row' 1, cell 1", bean.getGamma());
        assertEquals("row 1, cell 2", bean.getBeta());
        assertEquals("row 1, cell 3", bean.getAlpha());
    }

    /**
     * Error in date.
     */
    @Test
    public void errorInDate() {
        // Month and day in reverse order
        Reader reader = new StringReader(
                "text;year;number;date;year and month\n\"a bit of text\";1984;42.42;1972-13-01;2013-04\n");
        BeanReader<BeanWithVariousTypes> beanReader = new BeanReaderImpl<>(reader, BeanWithVariousTypes.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBeans();
        });
    }

    /**
     * Various data types.
     */
    @Test
    public void variousDataTypes() {
        Reader reader = new StringReader(
                "text;year;number;date;year and month\n" + "\"a bit of text\";1984;42.42;1972-01-13;2013-04\n");
        BeanReader<BeanWithVariousTypes> beanReader = new BeanReaderImpl<>(reader, BeanWithVariousTypes.class);
        List<BeanWithVariousTypes> beans = beanReader.readBeans();
        assertEquals(1, beans.size());
        BeanWithVariousTypes bean = beans.get(0);
        assertEquals("a bit of text", bean.getText());
        assertEquals((Integer) 1984, bean.getYear());
        assertEquals(Double.valueOf(42.42), bean.getNumber());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1972-01-13", formatter.format(bean.getDate()));
        formatter = new SimpleDateFormat("yyyy-MM");
        assertEquals("2013-04", formatter.format(bean.getYearMonth()));
    }

    /**
     * No header.
     */
    @Test
    public void noHeader() {
        Reader reader = new StringReader("\"a bit of text\";1984;42.42;1972-01-13;2013-04\n");
        BeanReader<BeanWithoutHeader> beanReader = new BeanReaderImpl<>(reader, BeanWithoutHeader.class);
        List<BeanWithoutHeader> beans = beanReader.readBeans();
        assertEquals(1, beans.size());
    }

    /**
     * Name matching.
     */
    @Test
    public void nameMatching() {
        Reader reader = new StringReader("street;CITY;postal code;ignore this\n"
                + "\"Some street\";\"Some city\";\"Some postal code\";\"Some ignoring\"");
        BeanReader<BeanWithNameMatching> beanReader = new BeanReaderImpl<>(reader, BeanWithNameMatching.class);
        List<BeanWithNameMatching> beans = beanReader.readBeans();
        assertEquals(1, beans.size());
        BeanWithNameMatching bean = beans.get(0);
        assertEquals("Some street", bean.getLine1());
        assertEquals("Some city", bean.getLine2());
        assertEquals("Some postal code", bean.getLine3());
    }

    /**
     * Name matching with bom.
     */
    @Test
    public void nameMatchingWithBom() {
        Reader reader = new StringReader("\uFEFFstreet;CITY;postal code;ignore this\n"
                + "\"Some street\";\"Some city\";\"Some postal code\";\"Some ignoring\"");
        BeanReader<BeanWithNameMatching> beanReader = new BeanReaderImpl<>(reader, BeanWithNameMatching.class);
        List<BeanWithNameMatching> beans = beanReader.readBeans();
        assertEquals(1, beans.size());
        BeanWithNameMatching bean = beans.get(0);
        assertEquals("Some street", bean.getLine1());
        assertEquals("Some city", bean.getLine2());
        assertEquals("Some postal code", bean.getLine3());
    }

    /**
     * Index matching.
     */
    @Test
    public void indexMatching() {
        Reader reader = new StringReader("\"line-1\";\"line0\";\"line1\";\"line2\";\"line3\"");
        BeanReader<BeanWithCustomIndexes> beanReader = new BeanReaderImpl<>(reader, BeanWithCustomIndexes.class);
        BeanWithCustomIndexes bean = beanReader.readBean();
        assertEquals("line0", bean.getLine0());
        assertEquals("line1", bean.getLine1());
        assertEquals("line2", bean.getLine2());
        assertEquals("line3", bean.getLine3());
    }

    /**
     * Number of ignores.
     */
    @Test
    public void numberOfIgnores() {
        Reader reader = new StringReader("14;28;42");
        BeanReader<BeanLotsOfIgnores> beanReader = new BeanReaderImpl<>(reader, BeanLotsOfIgnores.class);
        BeanLotsOfIgnores bean = beanReader.readBean();
        assertEquals((Integer) 14, bean.getTakeThis1());
        assertEquals((Integer) 28, bean.getPickThis1());
        assertEquals((Integer) 42, bean.getChooseThis1());
        assertNull(bean.getDitchThat1());
        assertNull(bean.getLeaveThat1());
    }

    /**
     * Custom property editor.
     */
    @Test
    public void customPropertyEditor() {
        Reader reader = new StringReader("\"some text\"");
        BeanReader<BeanWithConverter> beanReader = new BeanReaderImpl<>(reader, BeanWithConverter.class);
        BeanWithConverter bean = beanReader.readBean();
        assertEquals("some text", bean.getBean().getName());
    }

    /**
     * Illegal token.
     */
    @Test
    public void illegalToken() {
        Reader reader = new StringReader("\"alpha\";\"beta\";\"gamma\"a\n");
        BeanReader<BeanSimple> beanReader = new BeanReaderImpl<>(reader, BeanSimple.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBeans();
        });
    }

    /**
     * Bean mapping error.
     */
    @Test
    public void beanMappingError() {
        Reader reader = new StringReader("text;year;number;date;year and month\n"
                + "\"a bit of text\";UNEXPECTED TEXT!!!;42.42;1972-01-13;2013-04\n");
        BeanReader<BeanWithVariousTypes> beanReader = new BeanReaderImpl<>(reader, BeanWithVariousTypes.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBeans();
        });
    }

    /**
     * Cannot convert to non standard object.
     */
    @Test
    public void cannotConvertToNonStandardObject() {
        Reader reader = new StringReader("\"can I convert this to a simple bean?\"");
        BeanReader<BeanWithNonStandardObject> beanReader = new BeanReaderImpl<>(reader,
                BeanWithNonStandardObject.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBeans();
        });
    }

    /**
     * Non instantiable bean.
     */
    @Test
    public void nonInstantiableBean() {
        Reader reader = new StringReader("\"can I convert this to a simple bean?\"");
        BeanReader<BeanWithoutNoArgPublicConstructor> beanReader = new BeanReaderImpl<>(reader,
                BeanWithoutNoArgPublicConstructor.class);
        assertThrows(CsvException.class, () -> {
            beanReader.readBeans();
        });
    }

}
