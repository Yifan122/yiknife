package pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AliInvocationHandler implements InvocationHandler {
    /**
     * 目标对象
     */
    private Object target;

    public AliInvocationHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforePay();
        Object returnResult = method.invoke(target, args);
        afterPay();
        return returnResult;
    }

    private void afterPay() {
        System.out.println("付款给商家");
    }

    private void beforePay() {
        System.out.println("从银行取钱");
    }
}
