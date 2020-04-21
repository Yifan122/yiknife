package pattern.proxy.dynamic.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// Aspect 的创建
public class AlipayMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        beforePay();
        // 跟jdk相比，CGlib中可以直接用methodProxy的方法去调用被代理对象的方法
        Object returnResult = methodProxy.invokeSuper(proxy, args);
        afterPay();
        return returnResult;
    }

    private void afterPay() {
        System.out.println("CGLib付款给商家");
    }

    private void beforePay() {
        System.out.println("CGlib从银行取钱");
    }
}
