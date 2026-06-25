/*
 * SPDX-License-Identifier: Apache-2.0
 * See LICENSE file for details.
 *
 * Copyright 2013-2026 42 BV
 */
/**
 * The main interface of interaction with CSVeed for a developer. The most important interface here is
 * {@link org.csveed.api.CsvClient} which if a Facade for both {@link org.csveed.bean.BeanReader} and
 * {@link org.csveed.row.RowReader}. There should be no need for accessing those two directly, although it is possible.
 */
package org.csveed.api;
