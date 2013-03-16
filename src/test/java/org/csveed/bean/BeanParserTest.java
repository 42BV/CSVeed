package org.csveed.bean;

import org.csveed.common.Column;
import org.csveed.test.model.BeanLotsOfIgnores;
import org.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class BeanParserTest {

    @Test
    public void noGettersAndSetters() {
        BeanParser beanParser = new BeanParser();
        BeanReaderInstructions instructions = beanParser.getBeanInstructions(BeanWithoutGettersAndSetters.class);
        assertNull(((BeanReaderInstructionsImpl) instructions).getProperties().fromName(new Column("a")));
    }

    @Test
    public void caseInsensitivity() {
        BeanParser beanParser = new BeanParser();
        BeanReaderInstructions instructions = beanParser.getBeanInstructions(BeanLotsOfIgnores.class);
        assertNotNull(((BeanReaderInstructionsImpl) instructions).getProperties().fromName(new Column("takeThis1")));
        assertNotNull(((BeanReaderInstructionsImpl) instructions).getProperties().fromName(new Column("takethis1")));
    }

}
