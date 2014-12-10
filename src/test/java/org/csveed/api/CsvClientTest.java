package org.csveed.api;

import org.csveed.bean.BeanInstructionsImpl;
import org.csveed.bean.ColumnNameMapper;
import org.csveed.report.CsvException;
import org.csveed.test.converters.BeanSimpleConverter;
import org.csveed.test.model.*;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static junit.framework.Assert.*;

public class CsvClientTest {

    @Test
    public void writeBeansBasedOnClass() throws IOException {
        StringWriter writer = new StringWriter();
        List<BeanWithMultipleStrings> beans = new ArrayList<BeanWithMultipleStrings>();
        beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
        beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
        beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
        CsvClient<BeanWithMultipleStrings> client = new CsvClientImpl<BeanWithMultipleStrings>(
                writer, BeanWithMultipleStrings.class
        );
        client.writeBeans(beans);
        writer.close();
        assertEquals(
                "\"gamma\";\"beta\";\"alpha\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void writeBeansBasedOnInstructions() throws IOException {
        StringWriter writer = new StringWriter();
        List<BeanWithMultipleStrings> beans = new ArrayList<BeanWithMultipleStrings>();
        beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
        beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
        beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
        CsvClient<BeanWithMultipleStrings> client = new CsvClientImpl<BeanWithMultipleStrings>(
                writer, new BeanInstructionsImpl(BeanWithMultipleStrings.class)
                    .mapColumnNameToProperty("alpha", "alpha")
                    .mapColumnNameToProperty("beta", "beta")
                    .ignoreProperty("gamma")
        );
        client.writeBeans(beans);
        writer.close();
        assertEquals(
                "\"beta\";\"alpha\"\r\n"+
                "\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    private BeanWithMultipleStrings createBean(String alpha, String beta, String gamma) {
        BeanWithMultipleStrings bean = new BeanWithMultipleStrings();
        bean.setAlpha(alpha);
        bean.setBeta(beta);
        bean.setGamma(gamma);
        return bean;
    }

    @Test
    public void readAndWriteRows() throws IOException {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\n"
        );
        CsvClient csvReader = new CsvClientImpl(reader);
        List<Row> rows = csvReader.readRows();

        StringWriter writer = new StringWriter();
        CsvClient csvWriter = new CsvClientImpl(writer);
        csvWriter.writeHeader(rows.get(0).getHeader());
        csvWriter.writeRows(rows);
        writer.close();

        assertEquals(
                "\"alpha\";\"beta\";\"gamma\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void writeRow() throws IOException {
        StringWriter writer = new StringWriter();
        CsvClient csvClient = new CsvClientImpl(writer)
                .setUseHeader(false);
        csvClient.writeRow(new String[] { "alpha", "beta", "gamma" } );
        writer.close();
        assertEquals("\"alpha\";\"beta\";\"gamma\"\r\n", writer.getBuffer().toString());
    }

    @Test
    public void writeRows() throws IOException {
        StringWriter writer = new StringWriter();
        CsvClient csvClient = new CsvClientImpl(writer)
                .setUseHeader(false);
        csvClient.writeHeader(new String[] {
                "h1", "h2", "h3"
        } );
        csvClient.writeRows(new String[][] {
                { "l1c1", "l1c2", "l1c3" },
                { "l2c1", "l2c2", "l2c3" },
                { "l3c1", "l3c2", "l3c3" }
        } );
        writer.close();
        assertEquals(
                "\"h1\";\"h2\";\"h3\"\r\n"+
                "\"l1c1\";\"l1c2\";\"l1c3\"\r\n"+
                "\"l2c1\";\"l2c2\";\"l2c3\"\r\n"+
                "\"l3c1\";\"l3c2\";\"l3c3\"\r\n",
                writer.getBuffer().toString());
    }

    @Test
    public void WindowsCRLF0x0d0x0a() throws IOException {
        char[] file = new char[] {
            'n', 'a', 'm', 'e', 0x0d, 0x0a,
            'A', 'l', 'p', 'h', 'a', 0x0d, 0x0a,
            'B', 'e', 't', 'a', 0x0d, 0x0a,
            'G', 'a', 'm', 'm', 'a'
        };
        String fileText = new String(file);
        Reader reader = new StringReader(fileText);
        CsvClient<BeanSimple> csvClient = new CsvClientImpl<BeanSimple>(reader, BeanSimple.class);
        final List<BeanSimple> beans = csvClient.readBeans();
        assertEquals(3, beans.size());
    }

    @Test(expected = CsvException.class)
    public void doNotSkipCommentLineMustCauseColumnCheckToFail() {
        Reader reader = new StringReader(
                "name;name 2;name 3\n"+
                "# ignore me!\n"
        );
        CsvClient csvClient = new CsvClientImpl(reader)
                .skipCommentLines(false);
        csvClient.readRows();
    }

    @Test
    public void customComments() {
        Reader reader = new StringReader(
                "name\n"+
                "% ignore me!\n"+
                "some name\n"
        );
        CsvClient<BeanCustomComments> csvClient = new CsvClientImpl<BeanCustomComments>(reader, BeanCustomComments.class);
        List<BeanCustomComments> beans = csvClient.readBeans();
        assertEquals(1, beans.size());
    }

    @Test(expected = CsvException.class)
    public void callBeanMethodOnNonBeanReaderFacade() {
        Reader reader = new StringReader("");
        CsvClient csvClient = new CsvClientImpl(reader);
        csvClient.readBean();
    }

    @Test
    public void customNumberConversion() {
        Reader reader = new StringReader(
                "money\n"+
                "11.398,22"
        );
        CsvClient<BeanWithCustomNumber> beanReader = new CsvClientImpl<BeanWithCustomNumber>(reader, BeanWithCustomNumber.class)
                .setLocalizedNumber("number", Locale.GERMANY);
        BeanWithCustomNumber bean = beanReader.readBean();
        assertEquals(11398.22, bean.getNumber());
    }

    @Test
    public void readLines() {
        Reader reader = new StringReader(
                "text,year,number,date,lines,year and month\n"+
                "'a bit of text',1983,42.42,1972-01-13,'line 1',2013-04\n"+
                "'more text',1984,42.42,1972-01-14,'line 1\nline 2',2014-04\n"+
                "# please ignore me\n"+
                "'and yet more text',1985,42.42,1972-01-15,'line 1\nline 2\nline 3',2015-04\n"
        );

        CsvClient<BeanVariousNotAnnotated> csvClient =
                new CsvClientImpl<BeanVariousNotAnnotated>(reader, BeanVariousNotAnnotated.class)
                .setEscape('\\')
                .setQuote('\'')
                .setComment('#')
                .setEndOfLine(new char[]{'\n'})
                .setSeparator(',')
                .setStartRow(1)
                .setUseHeader(true)
                .setMapper(ColumnNameMapper.class)
                .ignoreProperty("ignoreMe")
                .mapColumnNameToProperty("text", "txt")
                .setRequired("txt", true)
                .mapColumnNameToProperty("year", "year")
                .mapColumnNameToProperty("number", "number")
                .mapColumnNameToProperty("date", "date")
                .setDate("date", "yyyy-MM-dd")
                .mapColumnNameToProperty("year and month", "yearMonth")
                .setDate("yearMonth", "yyyy-MM")
                .mapColumnNameToProperty("lines", "simple")
                .setConverter("simple", new BeanSimpleConverter())
                ;

        List<BeanVariousNotAnnotated> beans = csvClient.readBeans();
        assertTrue(csvClient.isFinished());
        assertEquals(6, csvClient.getCurrentLine());
        assertEquals(3, beans.size());
    }

    @Test
    public void multipleHeaderReads() {
        Reader reader = new StringReader(
                "text;year;number;date;lines;year and month\n"+
                "\"a bit of text\";1983;42.42;1972-01-13;\"line 1\";2013-04\n"+
                "\"more text\";1984;42.42;1972-01-14;\"line 1\nline 2\";2014-04\n"+
                "\"and yet more text\";1985;42.42;1972-01-15;\"line 1\nline 2\nline 3\";2015-04\n"
        );
        CsvClient<BeanVariousNotAnnotated> csvClient =
                new CsvClientImpl<BeanVariousNotAnnotated>(reader, BeanVariousNotAnnotated.class);

        assertNotNull(csvClient.readHeader());
        assertNotNull(csvClient.readHeader());
    }

    @Test(expected = CsvException.class)
    public void requiredField() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n"+
                "\"l1c1\";\"l1c2\";\"l1c3\"\n"+
                "\"l2c1\";\"l2c2\";\"l2c3\"\n"+
                "\"l3c1\";\"l3c2\";"
        );
        CsvClient<BeanWithMultipleStrings> csvClient =
                new CsvClientImpl<BeanWithMultipleStrings>(reader, BeanWithMultipleStrings.class)
                .setMapper(ColumnNameMapper.class)
                .setRequired("gamma", true);
        csvClient.readBeans();
    }

    @Test
    public void startAtLaterLine() {
        Reader reader = new StringReader(
                "-- ignore line 1\n"+
                "-- ignore line 2\n"+
                "-- ignore line 3\n"+
                "text;year;number;date;lines;year and month\n"+
                "\"a bit of text\";1983;42.42;1972-01-13;\"line 1\";2013-04\n"+
                "\"more text\";1984;42.42;1972-01-14;\"line 1\nline 2\";2014-04\n"+
                "\"and yet more text\";1985;42.42;1972-01-15;\"line 1\nline 2\nline 3\";2015-04\n"
        );
        CsvClient<BeanVariousNotAnnotated> csvClient =
                new CsvClientImpl<BeanVariousNotAnnotated>(reader, BeanVariousNotAnnotated.class)
                .setStartRow(4);
        List<Row> rows = csvClient.readRows();
        assertEquals(3, rows.size());
        assertEquals(8, csvClient.getCurrentLine());
    }

    @Test
    public void commentLinesNotSkipped() {
        Reader reader = new StringReader(
            "Issue ID;Submitter\n"+
            "#1;Bill\n"+
            "#2;Mary\n"+
            "#3;Jane\n"+
            "#4;Will"
        );
        CsvClient<BeanSimple> csvClient = new CsvClientImpl<BeanSimple>(reader, BeanSimple.class)
                .skipCommentLines(false);
        List<Row> rows = csvClient.readRows();
        assertEquals(4, rows.size());
    }

}
