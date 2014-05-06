package org.csveed.bean;

import org.csveed.row.RowWriter;

import java.util.Collection;

/**
* Class for writing Beans
*/
public interface BeanWriter<T> {

    /**
    * Writes a collection of Beans to the table
    * @param beans beans to write to the table
    */
    void writeBeans(Collection<T> beans);

    /**
    * Writes a single Bean to the table
    * @param bean bean to write to the table
    */
    void writeBean(T bean);

    RowWriter getRowWriter();

}
