package nl.tweeenveertig.csveed.bean.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class DefaultConverters {

    private Map<Class<?>, Converter> converters = new HashMap<Class<?>, Converter>();

    public DefaultConverters() {
        registerConverters();
    }

    public Converter getConverter(Class clazz) {
        return converters.get(clazz);
    }

    protected void registerConverters() {
        addConverter(Charset.class, new CharsetConverter());
        addConverter(Currency.class, new CurrencyConverter());
        addConverter(Pattern.class, new PatternConverter());
        addConverter(TimeZone.class, new TimeZoneConverter());
        addConverter(byte[].class, new ByteArrayConverter());
        addConverter(char[].class, new CharArrayConverter());
        addConverter(char.class, new CharacterConverter(false));
        addConverter(Character.class, new CharacterConverter(true));
        addConverter(boolean.class, new CustomBooleanConverter(false));
        addConverter(Boolean.class, new CustomBooleanConverter(true));
        addConverter(byte.class, new CustomNumberConverter(Byte.class, false));
        addConverter(Byte.class, new CustomNumberConverter(Byte.class, true));
        addConverter(short.class, new CustomNumberConverter(Short.class, false));
        addConverter(Short.class, new CustomNumberConverter(Short.class, true));
        addConverter(int.class, new CustomNumberConverter(Integer.class, false));
        addConverter(Integer.class, new CustomNumberConverter(Integer.class, true));
        addConverter(long.class, new CustomNumberConverter(Long.class, false));
        addConverter(Long.class, new CustomNumberConverter(Long.class, true));
        addConverter(float.class, new CustomNumberConverter(Float.class, false));
        addConverter(Float.class, new CustomNumberConverter(Float.class, true));
        addConverter(double.class, new CustomNumberConverter(Double.class, false));
        addConverter(Double.class, new CustomNumberConverter(Double.class, true));
        addConverter(BigDecimal.class, new CustomNumberConverter(BigDecimal.class, true));
        addConverter(BigInteger.class, new CustomNumberConverter(BigInteger.class, true));
        addConverter(String.class, new StringConverter());
    }

    protected void addConverter(Class clazz, Converter converter) {
        converters.put(clazz, converter);
    }

}
