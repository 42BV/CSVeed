package nl.tweeenveertig.csveed.bean.conversion;

import nl.tweeenveertig.csveed.bean.BeanParser;
import nl.tweeenveertig.csveed.bean.BeanProperties;
import nl.tweeenveertig.csveed.bean.BeanProperty;
import nl.tweeenveertig.csveed.bean.BeanReaderInstructionsImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BeanWrapperTest {

    private BeanProperties properties = deriveProperties();

    private Bean bean;

    private BeanWrapper beanWrapper;

    @Before
    public void init() {
        bean = new Bean();
        beanWrapper = new BeanWrapper(bean);
    }

    @Test
    public void hitAllProperties() throws Exception {
        beanWrapper.setProperty(properties.fromName("charset"), null);
        beanWrapper.setProperty(properties.fromName("chars"), null);
        beanWrapper.setProperty(properties.fromName("bytes"), null);
        beanWrapper.setProperty(properties.fromName("booleanObject"), "");
        beanWrapper.setProperty(properties.fromName("byteObject"), "");
        beanWrapper.setProperty(properties.fromName("shortObject"), "");
        beanWrapper.setProperty(properties.fromName("intObject"), "");
        beanWrapper.setProperty(properties.fromName("longObject"), "");
        beanWrapper.setProperty(properties.fromName("floatObject"), "");
        beanWrapper.setProperty(properties.fromName("doubleObject"), "");
        beanWrapper.setProperty(properties.fromName("bigDecimal"), "");
        beanWrapper.setProperty(properties.fromName("bigInteger"), "");
    }

    @Test
    public void charset() throws Exception {
        beanWrapper.setProperty(properties.fromName("charset"), "US-ASCII");
        assertEquals("US-ASCII", bean.getCharset().displayName());
    }

    @Test
    public void currency() throws Exception {
        beanWrapper.setProperty(properties.fromName("currency"), "USD");
        assertEquals("USD", bean.getCurrency().getCurrencyCode());
    }

    @Test
    public void pattern() throws Exception {
        beanWrapper.setProperty(properties.fromName("pattern"), "[0-9]");
        assertEquals("[0-9]", bean.getPattern().pattern());
    }

    @Test
    public void timeZone() throws Exception {
        beanWrapper.setProperty(properties.fromName("timeZone"), "GMT-8");
        assertEquals("GMT-08:00", bean.getTimeZone().getDisplayName());
    }

    @Test
    public void bytes() throws Exception {
        beanWrapper.setProperty(properties.fromName("bytes"), "ABC");
        assertTrue(Arrays.equals(new byte[] { 65, 66, 67 } , bean.getBytes()));
    }

    @Test
    public void chars() throws Exception {
        beanWrapper.setProperty(properties.fromName("chars"), "ABC");
        assertTrue(Arrays.equals(new char[] { 'A', 'B', 'C' } , bean.getChars()));
    }

    @Test
    public void charPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("charPrimitive"), "ü");
        assertEquals('ü', bean.getCharPrimitive());
    }

    @Test
    public void character() throws Exception {
        beanWrapper.setProperty(properties.fromName("character"), "ü");
        assertEquals('ü', (char)bean.getCharacter());
    }

    @Test
    public void booleanPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("booleanPrimitive"), "on");
        assertEquals(true, bean.isBooleanPrimitive());
    }

    @Test
    public void booleanObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("booleanObject"), "on");
        assertEquals(Boolean.TRUE, bean.getBooleanObject());
    }

    @Test
    public void bytePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("bytePrimitive"), "17");
        assertEquals(17, bean.getBytePrimitive());
    }

    @Test
    public void byteObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("byteObject"), "17");
        assertEquals(Byte.valueOf("17"), bean.getByteObject());
    }

    @Test
    public void shortPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("shortPrimitive"), "17");
        assertEquals(17, bean.getShortPrimitive());
    }

    @Test
    public void shortObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("shortObject"), "17");
        assertEquals(Short.valueOf("17"), bean.getShortObject());
    }

    @Test
    public void intPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("intPrimitive"), "1989");
        assertEquals(1989, bean.getIntPrimitive());
    }

    @Test
    public void intObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("intObject"), "1989");
        assertEquals((Integer)1989, bean.getIntObject());
    }

    @Test
    public void longPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("longPrimitive"), "1989");
        assertEquals(1989, bean.getLongPrimitive());
    }

    @Test
    public void longObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("longObject"), "1989");
        assertEquals((Long)1989L, bean.getLongObject());
    }

    @Test
    public void floatPrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("floatPrimitive"), "42.42");
        assertEquals((float)42.42, bean.getFloatPrimitive());
    }

    @Test
    public void floatObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("floatObject"), "42.42");
        assertEquals(Float.valueOf("42.42"), bean.getFloatObject());
    }

    @Test
    public void doublePrimitive() throws Exception {
        beanWrapper.setProperty(properties.fromName("doublePrimitive"), "42.42");
        assertEquals(42.42, bean.getDoublePrimitive());
    }

    @Test
    public void doubleObject() throws Exception {
        beanWrapper.setProperty(properties.fromName("doubleObject"), "42.42");
        assertEquals(Double.valueOf("42.42"), bean.getDoubleObject());
    }

    @Test
    public void bigDecimal() throws Exception {
        beanWrapper.setProperty(properties.fromName("bigDecimal"), "42.12345678901234567890");
        assertEquals(new BigDecimal("42.12345678901234567890"), bean.getBigDecimal());
    }

    @Test
    public void bigInteger() throws Exception {
        beanWrapper.setProperty(properties.fromName("bigInteger"), "4212345678901234567890");
        assertEquals(new BigInteger("4212345678901234567890"), bean.getBigInteger());
    }

    protected BeanProperties deriveProperties() {
        BeanReaderInstructionsImpl instructions = (BeanReaderInstructionsImpl)new BeanParser().getBeanInstructions(Bean.class);
        return instructions.getProperties();
    }
}
