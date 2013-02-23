package nl.tweeenveertig.csveed.bean;

import nl.tweeenveertig.csveed.test.model.BeanSimple;
import org.junit.Test;

import static junit.framework.Assert.assertNull;

public class BeanInstructionsTest {

    @Test
    public void propertyNameIsNull() {
        BeanInstructions<BeanSimple> instructions = new BeanInstructions<BeanSimple>(BeanSimple.class);
        assertNull(instructions.getBeanPropertyWithName("definitelyNotHere"));
    }

}
