package org.simpleframework.core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AspectExecutor implements MethodInterceptor {
    private Object target;
    private List<AspectInfo> sortedAspectInfoList;

    public AspectExecutor(Object target, List<AspectInfo> aspectInfoList) {
        this.target = target;
        this.sortedAspectInfoList = sort(aspectInfoList);
    }

    private List<AspectInfo> sort(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getIndex() - o2.getIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;

        invokeBeforeAdvices(method, args);
        try {
            returnValue = methodProxy.invokeSuper(proxy, args);
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Throwable e) {
            invokeAfterThrowable(method, args, e);
        }

        return returnValue;
    }

    /**
     * 降序执行afterThrowing
     *
     * @param method
     * @param args
     * @param e
     * @throws Throwable
     */
    private void invokeAfterThrowable(Method method, Object[] args, Throwable e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getDefaultAspect().afterThrowing(target, method, args);
        }
    }

    /**
     * 降序执行 afterReturning
     *
     * @param method
     * @param args
     * @param returnValue
     * @return
     * @throws Throwable
     */
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            returnValue = sortedAspectInfoList.get(i).getDefaultAspect().afterReturning(target, method, args, returnValue);
        }
        return returnValue;
    }

    /**
     * 升序执行before
     *
     * @param method
     * @param args
     * @throws Throwable
     */
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getDefaultAspect().before(target, method, args);
        }
    }


}
