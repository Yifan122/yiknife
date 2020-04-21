package pattern.proxy.statiz.impl;

import pattern.proxy.statiz.ToCPayment;

public class AliPay implements ToCPayment {
    private ToCPayment toCPayment;

    public AliPay(ToCPaymentImpl toCPayment) {
        this.toCPayment = toCPayment;
    }

    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();

    }

    private void afterPay() {
        System.out.println("付款给商家");
    }

    private void beforePay() {
        System.out.println("从银行取钱");
    }
}
