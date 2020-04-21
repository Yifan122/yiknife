package org.simpleframework.core.aop;

import java.lang.reflect.Method;

public abstract class DefaultAspect {
    public void before(Object target, Method method, Object[] args) throws Throwable {

    }

    public Object afterReturning(Object target, Method method, Object[] args, Object retrunValue) throws Throwable {
        return null;
    }

    public void afterThrowing(Object target, Method method, Object[] args) throws Throwable {

    }
}
