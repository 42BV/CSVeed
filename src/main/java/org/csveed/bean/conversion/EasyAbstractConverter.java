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
package org.csveed.bean.conversion;

public abstract class EasyAbstractConverter<K> extends AbstractConverter<K> {

    protected EasyAbstractConverter(Class<K> clazz) {
        super(clazz);
    }

    @Override
    public String toString(K value) throws Exception {
        return null;
    }

}
