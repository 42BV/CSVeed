package org.csveed.bean;

import org.csveed.test.model.BeanWithMultipleStrings;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

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
                "\"gamma\";\"beta\";\"alpha\"\r"+
                "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r"+
                "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r"+
                "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r",
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
