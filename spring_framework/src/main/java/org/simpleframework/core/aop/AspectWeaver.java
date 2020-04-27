package org.simpleframework.core.aop;

import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.aop.annotation.Aspect;
import org.simpleframework.core.aop.annotation.Order;
import org.simpleframework.util.CGlibUtil;

import java.lang.annotation.Annotation;
import java.util.*;

public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        // 1. 获取所有的切面类
        Set<Class<?>> aspectClassSet = beanContainer.getClassesByAnnotation(Aspect.class);
        validateAspectSet(aspectClassSet);
        // 2. 将切面类按照不同的目标进行切分
        Map<Class<? extends Annotation>, List<AspectInfo>> categoryAspectMap = categoryAspect(aspectClassSet);
        // 3. 按照不同的织入目标进行织入Aspect
        for (Class<? extends Annotation> targetAnnotation : categoryAspectMap.keySet()) {
            Set<Class<?>> classesByAnnotation = beanContainer.getClassesByAnnotation(targetAnnotation);
            for (Class<?> targetClass : classesByAnnotation) {
                AspectExecutor aspectExecutor = new AspectExecutor(targetClass, categoryAspectMap.get(targetAnnotation));
                Object proxy = CGlibUtil.createProxy(beanContainer.getBean(targetClass), aspectExecutor);
                beanContainer.addBean(targetClass, proxy);
            }
        }
    }

    private Map<Class<? extends Annotation>, List<AspectInfo>> categoryAspect(Set<Class<?>> aspectClassSet) {
        Map<Class<? extends Annotation>, List<AspectInfo>> categoryAspectMap = new HashMap<>();
        for (Class<?> aspect : aspectClassSet) {
            int order = aspect.getAnnotation(Order.class).value();
            Class<? extends Annotation> target = aspect.getAnnotation(Aspect.class).value();
            AspectInfo aspectInfo = new AspectInfo(order, (DefaultAspect) beanContainer.getBean(aspect));
            if (categoryAspectMap.containsKey(target)) {
                categoryAspectMap.get(target).add(aspectInfo);
            } else {
                List<AspectInfo> aspectInfoList = new ArrayList<>();
                aspectInfoList.add(aspectInfo);
                categoryAspectMap.put(target, aspectInfoList);
            }
        }
        return categoryAspectMap;
    }

    private void validateAspectSet(Set<Class<?>> aspectSet) {
        for (Class<?> aspect : aspectSet) {
            if (!validateAspect(aspect)) {
                throw new RuntimeException("The Aspect Class " + aspect.getClass().getName() + "is invalid. The possible reasons maybe: " +
                        "1. Not annotated by @Aspect; \n2. Not annotated by @Aspect. \n" +
                        "3. The class doesn't extend DefaultAspect \n4. It is a @Aspect annotate AspectExecutor class");
            }
        }
    }

    private boolean validateAspect(Class<?> aspect) {
        return aspect.isAnnotationPresent(Aspect.class) && aspect.isAnnotationPresent(Order.class)
                && DefaultAspect.class.isAssignableFrom(aspect) && !AspectExecutor.class.isAssignableFrom(aspect);
    }
}
