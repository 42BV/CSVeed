package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.csv.parser.SymbolMapping;

import java.util.ArrayList;
import java.util.List;

public class BeanInstructions<T> {

    private SymbolMapping symbolMapping = new SymbolMapping();

    private boolean useHeader = true;

    private int startRow = 0;

    private List<BeanProperty> properties = new ArrayList<BeanProperty>();

    private Class<T> beanClass;

    public BeanInstructions(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public T newInstance() {
        try {
            return this.beanClass.newInstance();
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    public SymbolMapping getSymbolMapping() {
        return symbolMapping;
    }

    public void setSymbolMapping(SymbolMapping symbolMapping) {
        this.symbolMapping = symbolMapping;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public void setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void addProperty(BeanProperty beanProperty) {
        this.properties.add(beanProperty);
    }

    public List<BeanProperty> getProperties() {
        return this.properties;
    }

    public BeanProperty getBeanPropertyWithIndex(int indexColumn) {
        for (BeanProperty beanProperty : getProperties()) {
            if (beanProperty.getIndexColumn() == indexColumn) {
                return beanProperty;
            }
        }
        return null;
    }

}
