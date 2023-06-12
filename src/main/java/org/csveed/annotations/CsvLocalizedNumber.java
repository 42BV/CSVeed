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
package org.csveed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Makes sure that a specific Locale is used to convert numbers. If no Locale is required, no annotation needs to be
 * set, because the right converter will be picked up automatically. If you still wish to apply a custom converter, use
 * {@link CsvConverter}.
 *
 * @author Robert Bor
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvLocalizedNumber {

    /**
     * Language used to construct Locale.
     *
     * @return language
     */
    String language();

    /**
     * Country used to construct Locale.
     *
     * @return country
     */
    String country() default "";

    /**
     * Variant used to construct Locale.
     *
     * @return variant
     */
    String variant() default "";
}
