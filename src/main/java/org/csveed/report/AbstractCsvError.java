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
package org.csveed.report;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCsvError implements CsvError {

    private String message;

    protected AbstractCsvError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    protected List<String> getMessageAsList() {
        List<String> lines = new ArrayList<>();
        lines.add(getMessage());
        return lines;
    }

}
