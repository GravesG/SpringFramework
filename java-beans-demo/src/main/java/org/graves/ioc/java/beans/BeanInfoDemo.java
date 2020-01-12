package org.graves.ioc.java.beans;

import java.beans.*;
import java.util.Properties;
import java.util.stream.Stream;

public class BeanInfoDemo {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);

        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    String propertyName = propertyDescriptor.getName();
                    if ("age".equals(propertyName)) {
                        // String -> Integer
                        propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
                    }
                });
    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {

        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
