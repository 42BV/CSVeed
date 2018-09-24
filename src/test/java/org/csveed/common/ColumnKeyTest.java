package org.csveed.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColumnKeyTest {

    @Test
    public void columnNameKeyEquals() {
        ColumnNameKey key1 = new ColumnNameKey("alpha");
        ColumnNameKey key2 = new ColumnNameKey("alpha");
        assertEquals(key1, key2);
    }

    @Test
    public void key1LessThanKey2() {
        ColumnNameKey key1 = new ColumnNameKey("alpha");
        ColumnNameKey key2 = new ColumnNameKey("beta");
        assertEquals(-1, key1.compareTo(key2));
    }

}
