package nl.tweeenveertig.csveed.bean.instructions;

import nl.tweeenveertig.csveed.bean.annotations.*;

import nl.tweeenveertig.csveed.csv.parser.EncounteredSymbol;
import nl.tweeenveertig.csveed.csv.parser.SymbolMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
*
*/
public class BeanParser<T> {

    public static final Logger LOG = LoggerFactory.getLogger(BeanParser.class);

    private Class<T> beanClass;

    private int indexColumn = 0;

    public BeanParser(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanInstructions<T> getBeanInstructions() {

        BeanInstructions beanInstructions = new BeanInstructions<T>(beanClass);

        Annotation[] annotations = this.beanClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvFile) {
                parseCsvFile(beanInstructions, (CsvFile)annotation);
            }
        }

        final BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(beanClass);
        } catch (IntrospectionException err) {
            throw new RuntimeException(err);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        // Note that we use getDeclaredFields here instead of the PropertyDescriptor order. The order we now
        // use is guaranteed to be the declaration order from JDK 6+, which is exactly what we need.
        for(Field field : this.beanClass.getDeclaredFields()) {
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(propertyDescriptors, field);
            if (propertyDescriptor.getWriteMethod() == null) {
                continue;
            }
            BeanProperty beanProperty = getBeanProperty(propertyDescriptor);
            if (beanProperty == null) {
                LOG.info("Ignoring "+beanClass.getName()+"."+field.getName());
            } else {
                beanInstructions.addProperty(beanProperty);
            }
        }

        return beanInstructions;
    }

    private PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] propertyDescriptors, Field field) {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (field.getName().equals(propertyDescriptor.getName())) {
                return propertyDescriptor;
            }
        }
        return null;
    }

    public BeanProperty getBeanProperty(PropertyDescriptor propertyDescriptor) {

        BeanProperty beanProperty = new BeanProperty();
        beanProperty.setPropertyDescriptor(propertyDescriptor);
        beanProperty.setName(propertyDescriptor.getName());

        final Field currentField;
        try {
            currentField = this.beanClass.getDeclaredField(propertyDescriptor.getName());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        Annotation[] annotations = currentField.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof CsvCell) {
                beanProperty = parseCsvCell(beanProperty, (CsvCell)annotation);
            } else if (annotation instanceof CsvConverter) {
                beanProperty = parseCsvConverter(beanProperty, (CsvConverter)annotation);
            } else if (annotation instanceof CsvDate) {
                beanProperty = parseCsvDate(beanProperty, (CsvDate)annotation);
            } else if (annotation instanceof CsvIgnore) {
                return null;
            }
        }
        beanProperty.setIndexColumn(indexColumn++);
        return beanProperty;
    }

    private BeanInstructions parseCsvFile(BeanInstructions beanInstructions, CsvFile csvFile) {

        SymbolMapping symbolMapping = new SymbolMapping();
        symbolMapping.addMapping(EncounteredSymbol.ESCAPE_SYMBOL, csvFile.escape());
        symbolMapping.addMapping(EncounteredSymbol.QUOTE_SYMBOL, csvFile.quote());
        symbolMapping.addMapping(EncounteredSymbol.SEPARATOR_SYMBOL, csvFile.separator());
        symbolMapping.addMapping(EncounteredSymbol.EOL_SYMBOL, csvFile.endOfLine());
        beanInstructions.setSymbolMapping(symbolMapping);

        beanInstructions.setMappingStrategy(csvFile.mappingStrategy());
        beanInstructions.setStartRow(csvFile.startRow());
        beanInstructions.setUseHeader(csvFile.useHeader());

        return beanInstructions;
    }

    private BeanProperty parseCsvDate(BeanProperty beanProperty, CsvDate csvDate) {
        DateFormat dateFormat = new SimpleDateFormat(csvDate.format());
        beanProperty.setConverter(new CustomDateEditor(dateFormat, true));
        return beanProperty;
    }

    private BeanProperty parseCsvConverter(BeanProperty beanProperty, CsvConverter csvConverter) {
        try {
            beanProperty.setConverter(csvConverter.converter().newInstance());
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
        return beanProperty;
    }

    private BeanProperty parseCsvCell(BeanProperty beanProperty, CsvCell csvCell) {
        beanProperty.setName((csvCell.name() == null || csvCell.name().equals("")) ? beanProperty.getName() : csvCell.name());
        beanProperty.setRequired(csvCell.required());
        indexColumn = csvCell.indexColumn() != -1 ? csvCell.indexColumn() : indexColumn;
        return beanProperty;
    }

}
