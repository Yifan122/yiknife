package org.simpleframework.core.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    // 针对不同的注解进行不同的织入
    // @Controller
    Class<? extends Annotation> value();
}
