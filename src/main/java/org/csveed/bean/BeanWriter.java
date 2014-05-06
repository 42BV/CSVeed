package org.csveed.bean;

import java.util.List;

/**
*
*/
public interface BeanWriter<T> {

    public void writeBeans(List<T> beans);

    public void writeBean(T bean);

}
