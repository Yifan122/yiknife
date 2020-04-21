package pattern.proxy.dynamic.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JDKProxyUtil {
    public static <T> T newInstance(T targetObject, InvocationHandler invocationHandler) {
        ClassLoader classLoader = targetObject.getClass().getClassLoader();
        Class<?>[] interfaces = targetObject.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
