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

/**
 * Stateless converter from String to Object
 *
 * @param <K>
 *            the Object to convert the String to
 *
 * @author Robert Bor
 */
public interface Converter<K> {

    K fromString(String text) throws Exception;

    String toString(K value) throws Exception;

    String infoOnType();

    Class<K> getType();

}
