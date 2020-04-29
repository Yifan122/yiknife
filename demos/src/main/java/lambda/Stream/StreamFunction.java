package lambda.Stream;

import com.alibaba.fastjson.JSON;
import lambda.entity.Sku;

import java.util.Comparator;
import java.util.List;

public class StreamFunction {
    private static List<Sku> skuList = Sku.getList();

    public static void testFilter() {
        skuList.stream()
                .filter(sku -> sku.getPrice() > 50)
                .forEach(sku -> System.out.println(JSON.toJSONString(sku, true)));
    }

    public static void testMap() {
        skuList.stream()
                .map(Sku::getName)
                .forEach(System.out::println);
    }

    public static void testMatchAll() {
        // Check all item's price is higher than 10
        boolean b = skuList.stream()
                .allMatch(sku -> sku.getPrice() > 10);
        System.out.println(b);
    }

    public static void testMatchAny() {
        boolean b = skuList.stream()
                .anyMatch(sku -> sku.getPrice() < 10);
        System.out.println(b);
    }

    public static void sorted() {
        skuList.stream()
                .sorted(Comparator.comparingDouble(Sku::getPrice))
                .forEach(sku -> System.out.println(JSON.toJSONString(sku)));
    }

    public static void main(String[] args) {
//        testFilter();
//        testMap();
//        testMatchAny();
//        testMatchAny();
        sorted();
    }
}
