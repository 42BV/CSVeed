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

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.csveed.test.model.BeanWithMultipleStrings;
import org.junit.jupiter.api.Test;

public class BeanWriterTest {

    @Test
    public void writeBeans() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            List<BeanWithMultipleStrings> beans = new ArrayList<>();
            beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
            beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
            beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
            BeanWriter<BeanWithMultipleStrings> beanWriter = new BeanWriterImpl<>(writer,
                    BeanWithMultipleStrings.class);
            beanWriter.writeBeans(beans);

            assertEquals(
                    "\"gamma\";\"beta\";\"alpha\"\r\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"
                            + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"
                            + "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
                    writer.getBuffer().toString());
        }
    }

    // https://github.com/42BV/CSVeed/issues/46
    @Test
    public void bug46ReportedByJnash67() throws IOException {
        try (StringWriter writer = new StringWriter()) {
            List<BeanWithMultipleStrings> beans = new ArrayList<>();
            beans.add(createBean("row 1, cell 3", "row 1, cell 2", "row 1, cell 1"));
            beans.add(createBean("row 2, cell 3", "row 2, cell 2", "row 2, cell 1"));
            beans.add(createBean("row 3, cell 3", "row 3, cell 2", "row 3, cell 1"));
            BeanInstructions bi = new BeanInstructionsImpl(BeanWithMultipleStrings.class);
            bi.logSettings();
            bi.mapColumnNameToProperty("Aap", "gamma");
            bi.mapColumnNameToProperty("Noot", "beta");
            bi.mapColumnNameToProperty("Mies", "alpha");
            BeanWriter<BeanWithMultipleStrings> beanWriter = new BeanWriterImpl<>(writer, bi);
            beanWriter.writeBeans(beans);

            assertEquals(
                    "\"Aap\";\"Noot\";\"Mies\"\r\n" + "\"row 1, cell 1\";\"row 1, cell 2\";\"row 1, cell 3\"\r\n"
                            + "\"row 2, cell 1\";\"row 2, cell 2\";\"row 2, cell 3\"\r\n"
                            + "\"row 3, cell 1\";\"row 3, cell 2\";\"row 3, cell 3\"\r\n",
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

}
