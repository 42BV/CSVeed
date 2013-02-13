package nl.tweeenveertig.csveed;

import nl.tweeenveertig.csveed.annotations.CsvDate;
import nl.tweeenveertig.csveed.annotations.CsvFile;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IntrospectionException, NoSuchMethodException {
        SomeClass sc = new SomeClass();
        BeanWrapperImpl bwi = new BeanWrapperImpl(sc);

        for(Field field : SomeClass.class.getDeclaredFields()){
            Class type = field.getType();
            String name = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof CsvDate) {
                    CsvDate csvDate = (CsvDate)annotation;
                    DateFormat dateFormat = new SimpleDateFormat(csvDate.format());
                    bwi.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
                }
            }
            System.out.println(type + "/" + name);
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(SomeClass.class);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println(pd.getPropertyType()+" "+pd.getName());
        }

        System.out.println("Separator:   "+ CsvFile.class.getMethod("quote").getDefaultValue());
        System.out.println("Delimiter:   "+ CsvFile.class.getMethod("delimiter").getDefaultValue());
        System.out.println("End-of-Line: "+ ((String[])CsvFile.class.getMethod("endOfLine").getDefaultValue()).length);
        System.out.println("Use header:  "+ CsvFile.class.getMethod("useHeader").getDefaultValue());
        System.out.println("Start row:   "+ CsvFile.class.getMethod("startRow").getDefaultValue());

        bwi.setPropertyValue("alpha", "100");
        bwi.setPropertyValue("beta", "1010101010101");
        bwi.setPropertyValue("gamma", "2013-01");

        System.out.println("Alpha "+sc.getAlpha());
        System.out.println("Beta: "+sc.getBeta());
        System.out.println("Gamma: "+sc.getGamma());

        System.out.println("the end");
    }


}
