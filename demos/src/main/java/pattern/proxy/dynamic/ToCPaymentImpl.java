package pattern.proxy.dynamic;

import pattern.proxy.statiz.ToCPayment;

public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("以人民的民意付钱");
    }
}
