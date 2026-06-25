/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.test.model.BeanLotsOfIgnores;
import org.csveed.test.model.BeanWithWrongAnnotation;
import org.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanParserTest.
 */
class BeanParserTest {

    /**
     * No getters and setters.
     */
    @Test
    void noGettersAndSetters() {
        BeanParser beanParser = new BeanParser();
        BeanInstructions instructions = beanParser.getBeanInstructions(BeanWithoutGettersAndSetters.class);
        assertNull(instructions.getProperties().fromName(new Column("a")));
    }

    /**
     * Case insensitivity.
     */
    @Test
    void caseInsensitivity() {
        BeanParser beanParser = new BeanParser();
        BeanInstructions instructions = beanParser.getBeanInstructions(BeanLotsOfIgnores.class);
        assertNotNull(instructions.getProperties().fromName(new Column("takeThis1")));
        assertNotNull(instructions.getProperties().fromName(new Column("takethis1")));
    }

    /**
     * Wrong annotation.
     */
    @Test
    void wrongAnnotation() {
        BeanParser beanParser = new BeanParser();
        assertThrows(CsvException.class, () -> beanParser.getBeanInstructions(BeanWithWrongAnnotation.class));
    }

}
