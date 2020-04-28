package lambda;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MethodReference {
    public static void main(String[] args) {
        List<Sku> skuList = new ArrayList<>();

        // Class :: static method
        skuList.sort(Sku::staticCompare);
        skuList.sort((Sku sku1, Sku sku2) -> sku1.getPrice() - sku2.getPrice());
        skuList.sort(Comparator.comparingInt(Sku::getPrice));
    }

    @AllArgsConstructor
    @Data
    static class Sku {
        private String name;
        private int price;

        public static int staticCompare(Sku sku1, Sku sku2) {
            return sku1.getPrice() - sku2.getPrice();
        }
    }
}
