package org.csveed.bean.conversion;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class EasyAbstractConverterTest {

    @Test
    public void testEasyAbstractConverter() throws Exception {
        Converter<Coordinate> converter = new EasyAbstractConverter<Coordinate>(Coordinate.class) {
            @Override
            public Coordinate fromString(String text) throws Exception {
                return Coordinate.fromString(text);
            }
        };
        Coordinate coords = converter.fromString("11/38");
        assertEquals((Integer)11, coords.getX());
        assertEquals((Integer)38, coords.getY());
    }
}
