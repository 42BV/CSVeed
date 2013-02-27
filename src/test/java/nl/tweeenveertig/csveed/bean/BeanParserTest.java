package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.Test;

import static junit.framework.Assert.assertNull;

public class BeanParserTest {

    @Test
    public void noGettersAndSetters() {
        BeanParser beanParser = new BeanParser();
        BeanReaderInstructions instructions = beanParser.getBeanInstructions(BeanWithoutGettersAndSetters.class);
        assertNull(((BeanReaderInstructionsImpl) instructions).getProperties().fromName("a"));
    }
}
