package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.Test;

import static junit.framework.Assert.assertNull;

public class BeanParserTest {

    @Test
    public void noGettersAndSetters() {
        BeanParser<BeanWithoutGettersAndSetters> beanParser =
                new BeanParser<BeanWithoutGettersAndSetters>(BeanWithoutGettersAndSetters.class);
        BeanInstructions<BeanWithoutGettersAndSetters> instructions = beanParser.getBeanInstructions();
        assertNull(instructions.getBeanPropertyWithName("a"));
    }
}
