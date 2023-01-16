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
/**
 * The main interface of interaction with CSVeed for a developer. The most important interface here is
 * {@link org.csveed.api.CsvClient} which if a Facade for both {@link org.csveed.bean.BeanReader} and
 * {@link org.csveed.row.RowReader}. There should be no need for accessing those two directly, although it is possible.
 */
package org.csveed.api;