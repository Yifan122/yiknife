package lambda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Sku {
    private String name;
    private int number;
    private double price;
    private Category categoryEnum;


    public static List<Sku> getList() {
        List<Sku> skuList = new ArrayList<>();
        skuList.add(new Sku("无人机", 2, 1999.0, Category.ELECTRONICS));
        skuList.add(new Sku("scala 入门", 1, 15.0, Category.BOOKS));
        skuList.add(new Sku("Spark 源码", 3, 56.5, Category.BOOKS));
        skuList.add(new Sku("Nike 跑鞋", 1, 623, Category.SPORTS));
        skuList.add(new Sku("s跑步机", 4, 6111.0, Category.SPORTS));
        skuList.add(new Sku("GPU", 5, 1259.3, Category.ELECTRONICS));

        return skuList;
    }
}
