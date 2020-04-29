package lambda.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Sku {
    private String name;
    private double price;
    private Category categoryEnum;


    public static List<Sku> getList() {
        List<Sku> skuList = new ArrayList<>();
        skuList.add(new Sku("无人机", 1999.0, Category.ELECTRONICS));
        skuList.add(new Sku("scala 入门", 15.0, Category.BOOKS));
        skuList.add(new Sku("Spark 源码", 56.5, Category.BOOKS));
        skuList.add(new Sku("Nike 跑鞋", 623, Category.SPORTS));
        skuList.add(new Sku("s跑步机", 6111.0, Category.SPORTS));
        skuList.add(new Sku("GPU", 1259.3, Category.ELECTRONICS));

        return skuList;
    }
}
