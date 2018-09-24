package org.csveed.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.csveed.bean.BeanInstructionsImpl;
import org.csveed.bean.ColumnNameMapper;
import org.csveed.report.CsvException;
import org.csveed.test.converters.BeanSimpleConverter;
import org.csveed.test.model.BeanCustomComments;
import org.csveed.test.model.BeanSimple;
import org.csveed.test.model.BeanVariousNotAnnotated;
import org.csveed.test.model.BeanWithCustomNumber;
import org.csveed.test.model.BeanWithMultipleStrings;
import org.junit.jupiter.api.Test;

public class CsvClientTest {

    @Test
    public void writeBeansBasedOnClass() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            List<BeanWithMultipleStrings> beans = new ArrayList<>();
            beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
            beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
            beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
            CsvClient<BeanWithMultipleStrings> client = new CsvClientImpl<>(
                    writer, BeanWithMultipleStrings.class
            );
            client.writeBeans(beans);
        
            assertEquals(
                "\"gamma\";\"beta\";\"alpha\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
        }
    }

    @Test
    public void writeBeansBasedOnInstructions() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            List<BeanWithMultipleStrings> beans = new ArrayList<>();
            beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
            beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
            beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
            CsvClient<BeanWithMultipleStrings> client = new CsvClientImpl<>(
                writer, new BeanInstructionsImpl(BeanWithMultipleStrings.class)
                    .mapColumnNameToProperty("alpha", "alpha")
                    .mapColumnNameToProperty("beta", "beta")
                    .ignoreProperty("gamma")
                    );
            client.writeBeans(beans);
        
            assertEquals(
                "\"beta\";\"alpha\"\r\n"+
                "\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
        }
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
        CsvClient<Reader> csvReader = new CsvClientImpl<>(reader);
        List<Row> rows = csvReader.readRows();

        try (StringWriter writer = new StringWriter()) {
            CsvClient<StringWriter> csvWriter = new CsvClientImpl<>(writer);
            csvWriter.writeHeader(rows.get(0).getHeader());
            csvWriter.writeRows(rows);

            assertEquals(
                "\"alpha\";\"beta\";\"gamma\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n",
                writer.getBuffer().toString());
        }
    }

    @Test
    public void writeRow() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            CsvClient<StringWriter> csvClient = new CsvClientImpl<StringWriter>(writer)
                .setUseHeader(false);
            csvClient.writeRow(new String[] { "alpha", "beta", "gamma" } );
            
            assertEquals("\"alpha\";\"beta\";\"gamma\"\r\n", writer.getBuffer().toString());
        }
    }

    @Test
    public void writeRowsLF() throws IOException {
        writeRows("\n");
    }

    @Test
    public void writeRowsCRLF() throws IOException {
        writeRows("\r\n");
    }

    private void writeRows(String lineTerminators) throws IOException {
        try (StringWriter writer = new StringWriter()) {
            CsvClient<StringWriter> csvClient = new CsvClientImpl<StringWriter>(writer)
                .setUseHeader(false)
                .setEndOfLine(lineTerminators.toCharArray());
            csvClient.writeHeader(new String[] {
                "h1", "h2", "h3"
            } );
            csvClient.writeRows(new String[][] {
                { "l1c1", "l1c2", "l1c3" },
                { "l2c1", "l2c2", "l2c3" },
                { "l3c1", "l3c2", "l3c3" }
            } );
        
            assertEquals(
                "\"h1\";\"h2\";\"h3\"" + lineTerminators +
                        "\"l1c1\";\"l1c2\";\"l1c3\"" + lineTerminators +
                        "\"l2c1\";\"l2c2\";\"l2c3\"" + lineTerminators +
                        "\"l3c1\";\"l3c2\";\"l3c3\"" + lineTerminators,
                writer.getBuffer().toString());
        }
    }

    @Test
    public void windowsCRLF0x0d0x0a() {
        char[] file = new char[] {
            'n', 'a', 'm', 'e', 0x0d, 0x0a,
            'A', 'l', 'p', 'h', 'a', 0x0d, 0x0a,
            'B', 'e', 't', 'a', 0x0d, 0x0a,
            'G', 'a', 'm', 'm', 'a'
        };
        String fileText = new String(file);
        Reader reader = new StringReader(fileText);
        CsvClient<BeanSimple> csvClient = new CsvClientImpl<>(reader, BeanSimple.class);
        final List<BeanSimple> beans = csvClient.readBeans();
        assertEquals(3, beans.size());
    }

    @Test
    public void doNotSkipCommentLineMustCauseColumnCheckToFail() {
        Reader reader = new StringReader(
                "name;name 2;name 3\n"+
                "# ignore me!\n"
        );
        CsvClient<StringWriter> csvClient = new CsvClientImpl<StringWriter>(reader)
                .skipCommentLines(false);
        assertThrows(CsvException.class, () ->  {
            csvClient.readRows();
        });
    }

    @Test
    public void customComments() {
        Reader reader = new StringReader(
                "name\n"+
                "% ignore me!\n"+
                "some name\n"
        );
        CsvClient<BeanCustomComments> csvClient = new CsvClientImpl<>(reader, BeanCustomComments.class);
        List<BeanCustomComments> beans = csvClient.readBeans();
        assertEquals(1, beans.size());
    }

    @Test
    public void callBeanMethodOnNonBeanReaderFacade() {
        Reader reader = new StringReader("");
        CsvClient<StringWriter> csvClient = new CsvClientImpl<>(reader);
        assertThrows(CsvException.class, () ->  {
            csvClient.readBean();
        });
    }

    @Test
    public void customNumberConversion() {
        Reader reader = new StringReader(
                "money\n"+
                "11.398,22"
        );
        CsvClient<BeanWithCustomNumber> beanReader = new CsvClientImpl<>(reader, BeanWithCustomNumber.class)
                .setLocalizedNumber("number", Locale.GERMANY);
        BeanWithCustomNumber bean = beanReader.readBean();
        assertEquals(Double.valueOf(11398.22), bean.getNumber());
    }

    @Test
    public void readLinesLF() {
        readLines("\n");
    }

    @Test
    public void readLinesCRLF() {
        readLines("\r\n");
    }

    private void readLines(String lineTerminators) {
        Reader reader = new StringReader("text,year,number,date,lines,year and month" + lineTerminators + "'a bit of text',1983,42.42,1972-01-13,'line 1',2013-04" + lineTerminators
                + "'more text',1984,42.42,1972-01-14,'line 1" + lineTerminators + "line 2',2014-04" + lineTerminators + "# please ignore me"
                + lineTerminators + "'and yet more text',1985,42.42,1972-01-15,'line 1" + lineTerminators + "line 2" + lineTerminators
                + "line 3',2015-04" + lineTerminators);

        CsvClient<BeanVariousNotAnnotated> csvClient =
                new CsvClientImpl<>(reader, BeanVariousNotAnnotated.class)
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
                new CsvClientImpl<>(reader, BeanVariousNotAnnotated.class);

        assertNotNull(csvClient.readHeader());
        assertNotNull(csvClient.readHeader());
    }

    @Test
    public void requiredField() {
        Reader reader = new StringReader(
                "alpha;beta;gamma\n"+
                "\"l1c1\";\"l1c2\";\"l1c3\"\n"+
                "\"l2c1\";\"l2c2\";\"l2c3\"\n"+
                "\"l3c1\";\"l3c2\";"
        );
        CsvClient<BeanWithMultipleStrings> csvClient =
                new CsvClientImpl<>(reader, BeanWithMultipleStrings.class)
                .setMapper(ColumnNameMapper.class)
                .setRequired("gamma", true);
        assertThrows(CsvException.class, () ->  {
            csvClient.readBeans();
        });
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
                new CsvClientImpl<>(reader, BeanVariousNotAnnotated.class)
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
        CsvClient<BeanSimple> csvClient = new CsvClientImpl<>(reader, BeanSimple.class)
                .skipCommentLines(false);
        List<Row> rows = csvClient.readRows();
        assertEquals(4, rows.size());
    }

    @Test
    public void headerNotWrittenForOtherwiseEmptyCsv() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            new CsvClientImpl<>(
                writer, BeanWithMultipleStrings.class
            );
        
            assertEquals("", writer.getBuffer().toString());
        }
    }

    @Test
    public void writeHeaderBasedOnBeanProperties() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            CsvClient<BeanWithMultipleStrings> client = new CsvClientImpl<>(
                writer, BeanWithMultipleStrings.class
                    );
            client.writeHeader();
        
            assertEquals("\"gamma\";\"beta\";\"alpha\"\r\n", writer.getBuffer().toString());
        }
    }
}
