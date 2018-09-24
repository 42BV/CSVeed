package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.csveed.test.model.BeanWithMultipleStrings;
import org.junit.jupiter.api.Test;

public class BeanWriterTest {
    
    @Test
    public void writeBeans() throws IOException {
        StringWriter writer = new StringWriter();
        List<BeanWithMultipleStrings> beans = new ArrayList<BeanWithMultipleStrings>();
        beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
        beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
        beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
        BeanWriter<BeanWithMultipleStrings> beanWriter =
                new BeanWriterImpl<BeanWithMultipleStrings>(writer, BeanWithMultipleStrings.class);
        beanWriter.writeBeans(beans);
        writer.close();
        assertEquals(
                "\"gamma\";\"beta\";\"alpha\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    // https://github.com/robert-bor/CSVeed/issues/46
    @Test
    public void bug46ReportedByJnash67() throws IOException {
        StringWriter writer = new StringWriter();
        List<BeanWithMultipleStrings> beans = new ArrayList<BeanWithMultipleStrings>();
        beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
        beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
        beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
        BeanInstructions bi = new BeanInstructionsImpl(BeanWithMultipleStrings.class);
        bi.logSettings();
        bi.mapColumnNameToProperty("Aap", "gamma");
        bi.mapColumnNameToProperty("Noot", "beta");
        bi.mapColumnNameToProperty("Mies", "alpha");
        BeanWriter<BeanWithMultipleStrings> beanWriter =
                new BeanWriterImpl<BeanWithMultipleStrings>(writer, bi);
        beanWriter.writeBeans(beans);
        writer.close();
        assertEquals(
                "\"Aap\";\"Noot\";\"Mies\"\r\n"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                writer.getBuffer().toString());
    }

    private BeanWithMultipleStrings createBean(String alpha, String beta, String gamma) {
        BeanWithMultipleStrings bean = new BeanWithMultipleStrings();
        bean.setAlpha(alpha);
        bean.setBeta(beta);
        bean.setGamma(gamma);
        return bean;
    }

}
