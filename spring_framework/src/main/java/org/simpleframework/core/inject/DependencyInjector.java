package org.simpleframework.core.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.inject.annotations.Autowired;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;

    public DependencyInjector() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        // Iterator all the class in the beanContainer
        Set<Class<?>> classes = beanContainer.getClasses();
        if (ValidationUtil.isEmpty(classes)) {
            log.warn("Bean Container is empty");
            return;
        }
        for (Class<?> clazz : classes) {
            // Find all the fields annotated by the @Autowired
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                // Get the value of the @Autowired
                Autowired autowired = field.getAnnotation(Autowired.class);
                String value = autowired.value();

                // Get the instance of that field
                Object obj = getFieldInstance(field.getType(), value);
                // Inject
                setField(beanContainer.getBean(clazz), field, obj, true);
            }
        }
    }

    /**
     * Get the field Instance or its implementation
     *
     * @param fieldClass
     * @param autowiredValue
     * @return
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object bean = beanContainer.getBean(fieldClass);
        if (bean != null) {
            return bean;
        }

        // Get the child class or the class which implements the interface
        Set<Class<?>> classesBySuper = beanContainer.getClassesBySuper(fieldClass);
        if (ValidationUtil.isEmpty(classesBySuper)) {
            log.warn(String.format("Cannot find the object of %s", fieldClass.getSimpleName()));
            return null;
        }

        if (classesBySuper.size() == 1) {
            return beanContainer.getBean(classesBySuper.iterator().next());
        }

        for (Class<?> clazz : classesBySuper) {
            if (clazz.getSimpleName().equals(autowiredValue)) {
                return beanContainer.getBean(clazz);
            }
        }

        throw new RuntimeException(String.format("Container has multiple object may extend or implement %s, please choose one",
                fieldClass.getSimpleName()));

    }

    /**
     * Inject instance to the field
     *
     * @param bean
     * @param field
     * @param value
     * @param accessible
     */
    private void setField(Object bean, Field field, Object value, boolean accessible) {
        field.setAccessible(accessible);
        try {
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            log.warn(String.format("Field %s of %s injection failed", field.getName(), bean.getClass().getSimpleName()));
            e.printStackTrace();
        }
    }
}
