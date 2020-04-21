package pattern.proxy.dynamic.jdk;

import pattern.proxy.dynamic.jdk.util.JDKProxyUtil;
import pattern.proxy.statiz.ToCPayment;

public class JDKMain {
    public static void main(String[] args) {
        ToCPayment toCPayment = new ToCPaymentImpl();
        AliInvocationHandler proxy = new AliInvocationHandler(toCPayment);
//        ClassLoader classLoader = ToCPaymentImpl.class.getClassLoader();
//        Class<?>[] interfaces = ToCPaymentImpl.class.getInterfaces();
//        Object o = Proxy.newProxyInstance(classLoader, interfaces, proxy);
//        ((ToCPayment)o).pay();
        JDKProxyUtil.newInstance(toCPayment, proxy).pay();
    }
}
