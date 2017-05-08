package org.csveed.bean.conversion;

import static org.junit.Assert.assertEquals;

import org.csveed.token.ParseState;
import org.junit.Test;

public class EnumConverterTest {

    @Test
    public void convertToEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<ParseState>(ParseState.class);
        assertEquals(ParseState.SKIP_LINE, converter.fromString("SKIP_LINE"));
    }

    @Test
    public void convertFromEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<ParseState>(ParseState.class);
        assertEquals("SKIP_LINE", converter.toString(ParseState.SKIP_LINE));
    }

}
