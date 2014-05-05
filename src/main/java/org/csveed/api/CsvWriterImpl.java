package org.csveed.api;

import java.io.Writer;

public class CsvWriterImpl<T> implements CsvWriter<T> {

//    private RowWriter rowWriter;

    public CsvWriterImpl(Writer writer) {
        
    }

    @Override
    public void writeRow(Row row) {

    }

//    public CsvWriterImpl(Writer writer, Class<T> beanClass) {
//        this(writer, new BeanParser().getBeanInstructions(beanClass));
//    }
//
//    public CsvWriterImpl(Writer writer, BeanInstructions beanInstructions) {
//
//    }

//    private BeanWriterImpl<T> getBeanWriter() {
//        if (this.beanReader == null) {
//            throw new CsvException(new GeneralError(
//                    "BeanWriter has not been initialized. Make sure to pass BeanInstructions or the bean class to CsvWriter."
//            ));
//        }
//        return this.beanReader;
//    }
//
//    private RowWriterImpl getRowWriter() {
//        return this.rowWriter;
//    }

}
