package org.csveed.bean;

import org.csveed.common.Column;
import org.csveed.report.CsvException;
import org.csveed.test.model.BeanLotsOfIgnores;
import org.csveed.test.model.BeanWithWrongAnnotation;
import org.csveed.test.model.BeanWithoutGettersAndSetters;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class BeanParserTest {

    @Test
    public void noGettersAndSetters() {
        BeanParser beanParser = new BeanParser();
        BeanInstructions instructions = beanParser.getBeanInstructions(BeanWithoutGettersAndSetters.class);
        assertNull(instructions.getProperties().fromName(new Column("a")));
    }

    @Test
    public void caseInsensitivity() {
        BeanParser beanParser = new BeanParser();
        BeanInstructions instructions = beanParser.getBeanInstructions(BeanLotsOfIgnores.class);
        assertNotNull(instructions.getProperties().fromName(new Column("takeThis1")));
        assertNotNull(instructions.getProperties().fromName(new Column("takethis1")));
    }

    @Test(expected = CsvException.class)
    public void wrongAnnotation() {
        BeanParser beanParser = new BeanParser();
        beanParser.getBeanInstructions(BeanWithWrongAnnotation.class);
    }

}
