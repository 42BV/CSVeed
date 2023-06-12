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

import java.util.List;

/**
 * Report on an error, always including at the very least an error message. If the error can be pinpointed to a line or
 * cell, this information is included as well.
 */
public interface CsvError {

    /**
     * Returns all lines, first the message, then the content and focus line, if available. These lines are usable to
     * print with a monospaced font, since the focus line will show in the line above where the error occurred. If lines
     * are needed for the browser, use {@link #getRowParts()}.
     *
     * @return all lines, in printable format. Will always contain at least one line.
     */
    List<String> getPrintableLines();

    /**
     * Gets the content line where the error occurred, if available. The line is split into RowPart entries. Every
     * RowPart knows about itself when it must be highlighted. This method is useful for reporting on the error in
     * another format, for example HTML or PDF, since you can control the highlighting.
     *
     * @return the content line split into RowPart entries. Will be an empty list if not available.
     */
    List<RowPart> getRowParts();

    /**
     * Returns the line number where the error occurred, zero-based. Will be -1 if not applicable.
     *
     * @return line number where the error occurred
     */
    int getLineNumber();

    /**
     * Returns the original error message.
     *
     * @return error message
     */
    String getMessage();

}
