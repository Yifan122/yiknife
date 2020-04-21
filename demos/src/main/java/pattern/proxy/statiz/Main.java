package pattern.proxy.statiz;

import pattern.proxy.statiz.impl.AliPay;
import pattern.proxy.statiz.impl.ToCPaymentImpl;

public class Main {
    public static void main(String[] args) {
        AliPay toCProxy = new AliPay(new ToCPaymentImpl());
        toCProxy.pay();
    }
}
