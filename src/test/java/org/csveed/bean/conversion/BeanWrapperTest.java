package org.csveed.bean.conversion;

import org.csveed.bean.BeanInstructions;
import org.csveed.bean.BeanParser;
import org.csveed.bean.BeanProperties;
import org.csveed.common.Column;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Currency;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class BeanWrapperTest {

    private BeanProperties properties = deriveProperties();

    private Bean bean;

    private DefaultConverters defaultConverters = new DefaultConverters();

    private BeanWrapper beanWrapper;

    @Before
    public void init() {
        bean = new Bean();
        beanWrapper = new BeanWrapper(defaultConverters, bean);
    }

    @Test
    public void hitAllProperties() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charset")),null);
        beanWrapper.setProperty(properties.fromName(new Column("charset")),"");
        beanWrapper.setProperty(properties.fromName(new Column("chars")),null);
        beanWrapper.setProperty(properties.fromName(new Column("bytes")),null);
        beanWrapper.setProperty(properties.fromName(new Column("booleanObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("byteObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("shortObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("intObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("longObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("floatObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("doubleObject")),"");
        beanWrapper.setProperty(properties.fromName(new Column("bigDecimal")),"");
        beanWrapper.setProperty(properties.fromName(new Column("bigInteger")),"");
    }

    @Test
    public void getCharset() throws Exception {
        bean.setCharset(Charset.forName("US-ASCII"));
        assertEquals("US-ASCII", beanWrapper.getProperty(properties.fromName(new Column("charset"))));
    }

    @Test
    public void setCharset() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charset")),"US-ASCII");
        assertEquals("US-ASCII", bean.getCharset().displayName());
    }

    @Test
    public void getCurrency() throws Exception {
        bean.setCurrency(Currency.getInstance("USD"));
        assertEquals("USD", beanWrapper.getProperty(properties.fromName(new Column("currency"))));
    }

    @Test
    public void setCurrency() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("currency")),"USD");
        assertEquals("USD", bean.getCurrency().getCurrencyCode());
    }

    @Test
    public void getPattern() throws Exception {
        bean.setPattern(Pattern.compile("[0-9]"));
        assertEquals("[0-9]", beanWrapper.getProperty(properties.fromName(new Column("pattern"))));
    }

    @Test
    public void setPattern() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("pattern")),"[0-9]");
        assertEquals("[0-9]", bean.getPattern().pattern());
    }

    @Test
    public void getTimeZone() throws Exception {
        bean.setTimeZone(TimeZone.getTimeZone("GMT-8"));
        assertEquals("GMT-08:00", beanWrapper.getProperty(properties.fromName(new Column("timeZone"))));
    }

    @Test
    public void setTimeZone() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("timeZone")),"GMT-8");
        assertEquals("GMT-08:00", bean.getTimeZone().getDisplayName());
    }

    @Test
    public void getBytes() throws Exception {
        bean.setBytes(new byte[]{65, 66, 67});
        assertEquals("ABC", beanWrapper.getProperty(properties.fromName(new Column("bytes"))));
    }

    @Test
    public void setBytes() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bytes")),"ABC");
        assertTrue(Arrays.equals(new byte[]{65, 66, 67}, bean.getBytes()));
    }

    @Test
    public void getChars() throws Exception {
        bean.setChars(new char[]{'A', 'B', 'C'});
        assertEquals("ABC", beanWrapper.getProperty(properties.fromName(new Column("chars"))));
    }

    @Test
    public void setChars() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("chars")),"ABC");
        assertTrue(Arrays.equals(new char[] { 'A', 'B', 'C' } , bean.getChars()));
    }

    @Test
    public void getCharPrimitive() throws Exception {
        bean.setCharPrimitive('ü');
        assertEquals("ü", beanWrapper.getProperty(properties.fromName(new Column("charPrimitive"))));
    }

    @Test
    public void setCharPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("charPrimitive")),"ü");
        assertEquals('ü', bean.getCharPrimitive());
    }

    @Test
    public void getCharacter() throws Exception {
        bean.setCharacter('ü');
        assertEquals("ü", beanWrapper.getProperty(properties.fromName(new Column("character"))));
    }

    @Test
    public void setCharacter() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("character")),"ü");
        assertEquals('ü', (char)bean.getCharacter());
    }

    @Test
    public void getBooleanPrimitive() throws Exception {
        bean.setBooleanPrimitive(true);
        assertEquals("true", beanWrapper.getProperty(properties.fromName(new Column("booleanPrimitive"))));
    }

    @Test
    public void setBooleanPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("booleanPrimitive")),"on");
        assertEquals(true, bean.isBooleanPrimitive());
    }

    @Test
    public void getBooleanObject() throws Exception {
        bean.setBooleanObject(Boolean.TRUE);
        assertEquals("true", beanWrapper.getProperty(properties.fromName(new Column("booleanObject"))));
    }

    @Test
    public void setBooleanObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("booleanObject")),"on");
        assertEquals(Boolean.TRUE, bean.getBooleanObject());
    }

    @Test
    public void getBytePrimitive() throws Exception {
        bean.setBytePrimitive((byte) 17);
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("bytePrimitive"))));
    }

    @Test
    public void setBytePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bytePrimitive")),"17");
        assertEquals(17, bean.getBytePrimitive());
    }

    @Test
    public void getByteObject() throws Exception {
        bean.setByteObject(Byte.valueOf("17"));
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("byteObject"))));
    }

    @Test
    public void setByteObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("byteObject")),"17");
        assertEquals(Byte.valueOf("17"),bean.getByteObject());
    }

    @Test
    public void getShortPrimitive() throws Exception {
        bean.setShortPrimitive((short) 17);
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("shortPrimitive"))));
    }

    @Test
    public void setShortPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("shortPrimitive")),"17");
        assertEquals(17, bean.getShortPrimitive());
    }

    @Test
    public void getShortObject() throws Exception {
        bean.setShortObject(Short.valueOf("17"));
        assertEquals("17", beanWrapper.getProperty(properties.fromName(new Column("shortObject"))));
    }

    @Test
    public void setShortObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("shortObject")),"17");
        assertEquals(Short.valueOf("17"),bean.getShortObject());
    }

    @Test
    public void getIntPrimitive() throws Exception {
        bean.setIntPrimitive(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("intPrimitive"))));
    }

    @Test
    public void setIntPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("intPrimitive")),"1989");
        assertEquals(1989, bean.getIntPrimitive());
    }

    @Test
    public void getIntObject() throws Exception {
        bean.setIntObject(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("intObject"))));
    }

    @Test
    public void setIntObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("intObject")),"1989");
        assertEquals((Integer)1989, bean.getIntObject());
    }

    @Test
    public void getLongPrimitive() throws Exception {
        bean.setLongPrimitive(989);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("longPrimitive"))));
    }

    @Test
    public void setLongPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("longPrimitive")),"1989");
        assertEquals(1989, bean.getLongPrimitive());
    }

    @Test
    public void getLongObject() throws Exception {
        bean.setLongObject(989L);
        assertEquals("989", beanWrapper.getProperty(properties.fromName(new Column("longObject"))));
    }

    @Test
    public void setLongObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("longObject")),"1989");
        assertEquals((Long)1989L, bean.getLongObject());
    }

    @Test
    public void getFloatPrimitive() throws Exception {
        bean.setFloatPrimitive((float)42.42);
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("floatPrimitive"))));
    }

    @Test
    public void setFloatPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("floatPrimitive")),"42.42");
        assertEquals((float)42.42, bean.getFloatPrimitive(), 1);
    }

    @Test
    public void getFloatObject() throws Exception {
        bean.setFloatObject(Float.valueOf("42.42"));
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("floatObject"))));
    }

    @Test
    public void setFloatObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("floatObject")),"42.42");
        assertEquals(Float.valueOf("42.42"),bean.getFloatObject());
    }

    @Test
    public void getDoublePrimitive() throws Exception {
        bean.setDoublePrimitive(42.42);
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("doublePrimitive"))));
    }

    @Test
    public void setDoublePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("doublePrimitive")),"42.42");
        assertEquals(42.42, bean.getDoublePrimitive(), 1);
    }

    @Test
    public void getDoubleObject() throws Exception {
        bean.setDoubleObject(Double.valueOf("42.42"));
        assertEquals("42.42", beanWrapper.getProperty(properties.fromName(new Column("doubleObject"))));
    }

    @Test
    public void setDoubleObject() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("doubleObject")),"42.42");
        assertEquals(Double.valueOf("42.42"),bean.getDoubleObject());
    }

    @Test
    public void getBigDecimal() throws Exception {
        bean.setBigDecimal(new BigDecimal("42.123"));
        assertEquals("42.123", beanWrapper.getProperty(properties.fromName(new Column("bigDecimal"))));
    }

    @Test
    public void setBigDecimal() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bigDecimal")),"42.12345678901234567890");
        assertEquals(new BigDecimal("42.12345678901234567890"),bean.getBigDecimal());
    }

    @Test
    public void getBigInteger() throws Exception {
        bean.setBigInteger(new BigInteger("4212345678901234567890"));
        assertEquals("4212345678901234567890", beanWrapper.getProperty(properties.fromName(new Column("bigInteger"))));
    }

    @Test
    public void setBigInteger() throws Exception {
        beanWrapper.setProperty(properties.fromName(new Column("bigInteger")),"4212345678901234567890");
        assertEquals(new BigInteger("4212345678901234567890"),bean.getBigInteger());
    }

    protected BeanProperties deriveProperties() {
        BeanInstructions instructions = new BeanParser().getBeanInstructions(Bean.class);
        return instructions.getProperties();
    }
}
