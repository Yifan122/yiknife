package pattern.proxy.dynamic.cglib.util;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CGlibUtil {
    public static <T> T createProxy(T target, MethodInterceptor methodInterceptor) {
        return (T) Enhancer.create(target.getClass(), methodInterceptor);
    }
}
