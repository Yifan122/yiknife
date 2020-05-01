package lambda.Stream;

import com.alibaba.fastjson.JSON;
import lambda.entity.Sku;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class StreamFunction {
    private List<Sku> skuList = Sku.getList();

    @Test
    public void testFilter() {
        skuList.stream()
                .filter(sku -> sku.getPrice() > 50)
                .forEach(sku -> System.out.println(JSON.toJSONString(sku, true)));
    }

    @Test
    public void testMap() {
        skuList.stream()
                .map(Sku::getName)
                .forEach(System.out::println);
    }

    @Test
    public void testMatchAll() {
        // Check all item's price is higher than 10
        boolean b = skuList.stream()
                .allMatch(sku -> sku.getPrice() > 10);
        System.out.println(b);
    }

    @Test
    public void testMatchAny() {
        boolean b = skuList.stream()
                .anyMatch(sku -> sku.getPrice() < 10);
        System.out.println(b);
    }

    @Test
    public void sorted() {
        skuList.stream()
                .sorted(Comparator.comparingDouble(Sku::getPrice))
                .forEach(sku -> System.out.println(JSON.toJSONString(sku)));
    }
}
