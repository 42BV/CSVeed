package org.csveed.bean.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.csveed.token.ParseState;
import org.junit.jupiter.api.Test;

public class EnumConverterTest {

    @Test
    public void convertToEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<>(ParseState.class);
        assertEquals(ParseState.SKIP_LINE, converter.fromString("SKIP_LINE"));
    }

    @Test
    public void convertFromEnum() throws Exception {
        EnumConverter<ParseState> converter = new EnumConverter<>(ParseState.class);
        assertEquals("SKIP_LINE", converter.toString(ParseState.SKIP_LINE));
    }

}
