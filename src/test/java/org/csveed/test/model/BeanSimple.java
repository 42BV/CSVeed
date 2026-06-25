/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.test.model;

import org.csveed.test.annotations.IgnoreClass;
import org.csveed.test.annotations.IgnoreField;

/**
 * The Class BeanSimple.
 */
@IgnoreClass
public class BeanSimple {

    /** The name. */
    @IgnoreField
    public String name;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
