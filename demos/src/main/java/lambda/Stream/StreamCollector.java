package lambda.Stream;

import com.alibaba.fastjson.JSON;
import lambda.entity.Category;
import lambda.entity.Sku;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollector {
    List<Sku> skuList = Sku.getList();

    @Test
    public void group() {
        Map<Category, List<Sku>> map = skuList.stream()
                .collect(
                        Collectors.groupingBy(sku -> sku.getCategoryEnum())
                );

        System.out.println(JSON.toJSONString(map, true));
    }

    @Test
    public void partition() {
        Map<Boolean, List<Sku>> map = skuList.stream()
                .collect(Collectors.partitioningBy(sku -> sku.getPrice() > 100));

        System.out.println(JSON.toJSONString(map, true));
    }
}
