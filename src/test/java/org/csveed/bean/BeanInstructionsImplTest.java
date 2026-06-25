/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.csveed.common.Column;
import org.csveed.test.model.BeanSimple;
import org.junit.jupiter.api.Test;

/**
 * The Class BeanInstructionsImplTest.
 */
class BeanInstructionsImplTest {

    /**
     * Property name is null.
     */
    @Test
    void propertyNameIsNull() {
        BeanInstructions instructions = new BeanInstructionsImpl(BeanSimple.class);
        assertNull(instructions.getProperties().fromName(new Column("definitelyNotHere")));
    }

}
