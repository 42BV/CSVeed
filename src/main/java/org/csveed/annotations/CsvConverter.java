/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
package org.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.csveed.bean.conversion.Converter;

/**
 * Sets a custom converter for the field. The converter must be of type Converter.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvConverter {

    /**
     * The Converter to use for the field.
     *
     * @return PropertyEditor
     */
    Class<? extends Converter> converter();

}
