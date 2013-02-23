package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.Test;

import static junit.framework.Assert.assertNull;

public class BeanParserTest {

    @Test
    public void noGettersAndSetters() {
        BeanParser<BeanWithoutGettersAndSetters> beanParser =
                new BeanParser<BeanWithoutGettersAndSetters>();
        BeanInstructions<BeanWithoutGettersAndSetters> instructions = beanParser.getBeanInstructions(BeanWithoutGettersAndSetters.class);
        assertNull(instructions.getBeanPropertyWithName("a"));
    }
}
