package lambda.Stream;

import com.alibaba.fastjson.JSON;
import lambda.entity.Category;
import lambda.entity.Sku;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StreamReduce {
    List<Sku> skuList = Sku.getList();

    @Test
    public void reduceTest() {

        Sku reduce = skuList.stream()
                //.parallel()
                .reduce(
                        new Sku("Total", 0, 0, Category.BOOKS),
                        (Sku sku1, Sku sku2) -> {
                            int totalNumber = sku1.getNumber() + sku2.getNumber();
                            double totalPrice = sku1.getPrice() * sku1.getNumber() + sku2.getPrice() * sku2.getNumber();
                            return new Sku("Totoal", totalNumber, totalPrice, Category.BOOKS);
                        },
                        // 并行情况下， 多个并行结果的合并
                        (Sku sku1, Sku sku2) -> {
                            System.out.println("执行合并方法");
                            int totalNumber = sku1.getNumber() + sku2.getNumber();
                            double totalPrice = sku1.getPrice() * sku1.getNumber() + sku2.getPrice() * sku2.getNumber();
                            return new Sku("Totoal", totalNumber, totalPrice, Category.BOOKS);
                        }
                );

        System.out.println(JSON.toJSONString(reduce, true));

    }
}
