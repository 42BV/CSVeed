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

import java.util.Set;

import org.csveed.api.Header;
import org.csveed.api.Row;
import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.report.RowError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColumnNameMapper<T> extends AbstractMapper<T> {

    private static final Logger logger = LoggerFactory.getLogger(ColumnNameMapper.class);

    @Override
    protected Set<Column> keys() {
        return beanInstructions.getProperties().columnNameKeys();
    }

    @Override
    public BeanProperty getBeanProperty(Column column) {
        return beanInstructions.getProperties().fromName(column);
    }

    @Override
    protected void checkKey(Header header, Column key) {
        try {
            header.getIndex(key.getColumnName());
        } catch (CsvException e) {
            logger.trace("", e);
            throw new CsvException(new RowError("The header row does not contain column \"" + key
                    + "\". Originally mapped to property \"" + getBeanProperty(key).getPropertyName() + "\"",
                    header.reportOnEndOfLine(), 0));
        }
    }

    @Override
    protected Column getColumn(Row row) {
        return new Column().setHeader(row.getHeader());
    }

}
