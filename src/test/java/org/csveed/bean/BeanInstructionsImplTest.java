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
