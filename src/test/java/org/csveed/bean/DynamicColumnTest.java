package org.csveed.bean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.csveed.common.Column;
import org.junit.jupiter.api.Test;

public class DynamicColumnTest {

    @Test
    public void advanceAndReset() {
        int startColumn = 5;
        int numberOfColumns = 7;
        DynamicColumn column = new DynamicColumn(new Column(startColumn));
        for (int i = 0; i < numberOfColumns - startColumn + 1; i++) {
            column.advanceDynamicColumn();
            assertFalse(column.atFirstDynamicColumn(), "Must not be at first column now");
            column.checkForReset(numberOfColumns);
        }
        assertTrue(column.atFirstDynamicColumn(), "Must be at first dynamic column now");
    }

    @Test
    public void weHaveNoDynamicColumns() {
        DynamicColumn column = new DynamicColumn(null);
        column.advanceDynamicColumn(); // should have no effect
        assertTrue(column.atFirstDynamicColumn(), "Must be at first dynamic column now"); // always the case if empty
    }

    @Test
    public void activeDynamicColumns() {
        Column activeColumn = new Column(4);
        Column inactiveColumn = new Column(5);
        DynamicColumn dynamicColumn = new DynamicColumn(activeColumn);
        assertTrue(dynamicColumn.isDynamicColumnActive(activeColumn), "Column "+activeColumn.getColumnIndex()+" must be active");
        assertFalse(dynamicColumn.isDynamicColumnActive(inactiveColumn), "Column "+inactiveColumn.getColumnIndex()+" must be active");
    }
}
