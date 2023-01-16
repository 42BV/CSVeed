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
 * The package responsible for reading a CSV line from a {@link java.io.Reader} and converting this to tokens. The
 * tokens are translated to lines by a separate process. The tokenizer is highly configurable with regards to the
 * symbols, skipping instructions and the start line.
 */
package org.csveed.token;