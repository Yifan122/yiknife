package pattern.proxy.dynamic.cglib;

import org.simpleframework.util.CGlibUtil;
import pattern.proxy.dynamic.jdk.ToCPaymentImpl;
import pattern.proxy.statiz.ToCPayment;

public class CGlibMain {
    public static void main(String[] args) {
        ToCPayment toCPayment = new ToCPaymentImpl();
        ToCPayment proxy = CGlibUtil.createProxy(toCPayment, new AlipayMethodInterceptor());
        proxy.pay();
    }
}
