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

public class RowPart {

    private String token;

    private boolean highlight;

    public RowPart(String token, boolean highlight) {
        this.token = token;
        this.highlight = highlight;
    }

    public String getToken() {
        return token;
    }

    public boolean isHighlight() {
        return highlight;
    }
}
