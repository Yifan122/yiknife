package com.bigdata.hbase.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class JavaSparkTest {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("appName").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = sc.parallelize(data).collect();

        collect.stream().forEach(System.out::println);
    }
}
