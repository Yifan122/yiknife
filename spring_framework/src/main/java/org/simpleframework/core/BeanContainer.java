package org.simpleframework.core;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotations.Component;
import org.simpleframework.core.annotations.Controller;
import org.simpleframework.core.annotations.Repository;
import org.simpleframework.core.annotations.Service;
import org.simpleframework.core.aop.annotation.Aspect;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor
public class BeanContainer {
    // ANNOTATION_LIST store the which kind of objects need to store into BeanContainer
    // BEAN_ANNOTATION should be immutable collection
    private final static List<Class<? extends Annotation>> BEAN_ANNOTATION = Collections.unmodifiableList(Arrays.asList(Component.class, Controller.class,
            Repository.class, Service.class, Aspect.class));
    // map store the object instance
    private final ConcurrentHashMap<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    private boolean load = false;

    private enum Container {
        HOLD;
        BeanContainer instance = Container();

        private BeanContainer Container() {
            System.out.println("Container created");
            return new BeanContainer();
        }
    }

    /**
     * Get container instance
     *
     * @return
     */
    public static BeanContainer getInstance() {
        return Container.HOLD.instance;
    }

    public boolean isLoad() {
        return load;
    }

    public int size() {
        return beanMap.size();
    }

    public synchronized void loadContainer(String packageNmae) {
        if (isLoad()) {
            return;
        }

        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageNmae);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn(String.format("The package %s is empty package.", packageNmae));
            return;
        }

        // Filter the class which has annotation in ANNOTATION_LIST
        for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
            for (Class<?> clazz : classSet) {
                if (clazz.isAnnotationPresent(annotation)) {
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        load = true;
    }

    /**
     * Add class in container
     *
     * @param clazz
     * @param obj
     * @return
     */
    public Object addBean(Class<?> clazz, Object obj) {
        return beanMap.put(clazz, obj);
    }

    /**
     * Remove class in container
     *
     * @param clazz
     * @return
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * Get bean
     *
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * Get all the class
     *
     * @return
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * Get all the values
     *
     * @return
     */
    public Set<Object> getValues() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * Get all the classes with given annotation
     *
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> classes = getClasses();
        if (ValidationUtil.isEmpty(classes)) {
            log.warn("BeanMap is empty");
            return null;
        }

        Set<Class<?>> annotatedClasses = new HashSet<>();

        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(annotation)) {
                annotatedClasses.add(clazz);
            }
        }

        return annotatedClasses.size() > 0 ? annotatedClasses : null;
    }

    /**
     * Get the class which is child of the given class
     * or has implements the interface
     *
     * @param interfaceOrClass
     * @return
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        Set<Class<?>> classes = getClasses();
        if (ValidationUtil.isEmpty(classes)) {
            log.warn("BeanMap is empty");
            return null;
        }

        Set<Class<?>> childrenClassSet = new HashSet<>();

        for (Class<?> clazz : classes) {
            if (interfaceOrClass.isAssignableFrom(clazz) && !interfaceOrClass.equals(clazz)) {
                childrenClassSet.add(clazz);
            }
        }

        return childrenClassSet.size() > 0 ? childrenClassSet : null;
    }




}
