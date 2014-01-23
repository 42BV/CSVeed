package org.csveed.bean;

import org.csveed.common.Column;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DynamicColumnTest {

    @Test
    public void advanceAndReset() {
        int startColumn = 5;
        int numberOfColumns = 7;
        DynamicColumn column = new DynamicColumn(new Column(startColumn));
        for (int i = 0; i < numberOfColumns - startColumn + 1; i++) {
            column.advanceDynamicColumn();
            assertFalse("Must not be at first column now", column.atFirstDynamicColumn());
            column.checkForReset(numberOfColumns);
        }
        assertTrue("Must be at first dynamic column now", column.atFirstDynamicColumn());
    }

    @Test
    public void weHaveNoDynamicColumns() {
        DynamicColumn column = new DynamicColumn(new Column(0));
        column.advanceDynamicColumn(); // should have no effect
        assertTrue("Must be at first dynamic column now", column.atFirstDynamicColumn()); // always the case if empty
    }

    @Test
    public void activeDynamicColumns() {
        Column activeColumn = new Column(4);
        Column inactiveColumn = new Column(5);
        DynamicColumn dynamicColumn = new DynamicColumn(activeColumn);
        assertTrue("Column "+activeColumn.getColumnIndex()+" must be active", dynamicColumn.isDynamicColumnActive(activeColumn));
        assertFalse("Column "+inactiveColumn.getColumnIndex()+" must be active", dynamicColumn.isDynamicColumnActive(inactiveColumn));

    }
}
