package com.bigdata.hbase.example;

import com.bigdata.hbase.util.HBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class HBaseApp {
    private static Connection connection = null;
    private static Table table = null;
    private static Admin admin = null;

    private final String TABLE_NAME = "equity_data";

    @BeforeAll
    static void init() {
        BasicConfigurator.configure();

        Configuration configuration = new Configuration();
        configuration.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        configuration.set("hbase.zookeeper.quorum", "localhost:2181");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();

            Assertions.assertNotNull(connection);
            Assertions.assertNotNull(admin);

        } catch (Exception e) {
            log.error("Failed to get the Hbase connection");
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            admin.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCoon() {

    }

    @Test
    void listTable() {
        try {
            TableName[] tableNames = admin.listTableNames();
            Arrays.stream(tableNames)
                    .map(tableName -> Bytes.toString(tableName.getName()))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createTable() throws IOException {
        TableName tableName = TableName.valueOf(TABLE_NAME);
        if (admin.isTableAvailable(tableName)) {
            log.error("Table {} already exists", TABLE_NAME);
        } else {
            TableDescriptor descriptor = TableDescriptorBuilder
                    .newBuilder(tableName)
                    .setColumnFamilies(Arrays.asList(
                            new HColumnDescriptor("ot_system"),
                            new HColumnDescriptor("values"),
                            new HColumnDescriptor("object_name")
                    ))
                    .build();
            admin.createTable(descriptor);
            log.info("Table {} build successfully", TABLE_NAME);
        }
    }

    @Test
    void testPut() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Put put = new Put(Bytes.toBytes("yifan"));

        put.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("OT"), Bytes.toBytes("5"));
        put.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("SOPHIS"), Bytes.toBytes("maket"));
        put.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("JV"), Bytes.toBytes("Socket"));

        put.addColumn(Bytes.toBytes("values"), Bytes.toBytes("OT"), Bytes.toBytes("18"));
        put.addColumn(Bytes.toBytes("values"), Bytes.toBytes("SOPHIS"), Bytes.toBytes("zookeeper"));

        put.addColumn(Bytes.toBytes("object_name"), Bytes.toBytes("IR_Delta"), Bytes.toBytes("5"));
        put.addColumn(Bytes.toBytes("object_name"), Bytes.toBytes("Vega"), Bytes.toBytes("6"));
        put.addColumn(Bytes.toBytes("object_name"), Bytes.toBytes("Gamma"), Bytes.toBytes("7"));

        table.put(put);
    }

    @Test
    void testPuts() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        List<Put> puts = new ArrayList<>();

        Put put1 = new Put(Bytes.toBytes("zhangsan"));

        put1.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("OT"), Bytes.toBytes("7"));
        put1.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("SOPHIS"), Bytes.toBytes("aaa"));
        put1.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("JV"), Bytes.toBytes("Socket"));

        Put put2 = new Put(Bytes.toBytes("lisi"));

        put2.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("OT"), Bytes.toBytes("7"));
        put2.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("SOPHIS"), Bytes.toBytes("aaa"));
        put2.addColumn(Bytes.toBytes("ot_system"), Bytes.toBytes("JV"), Bytes.toBytes("Socket"));

        puts.add(put1);
        puts.add(put2);

        table.put(puts);
    }

    @Test
    void testGet() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Get get = new Get(Bytes.toBytes("yifan"));
        get.addFamily(Bytes.toBytes("ot_system"));
        Result result = table.get(get);

        HBaseUtil.printResult(result);
    }

    @Test
    void testScan() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Scan scan = new Scan();
        ResultScanner rs = table.getScanner(scan);

        for (Result result : rs) {
            HBaseUtil.printResult(result);
        }
    }

    @Test
    void testFilter() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Scan scan = new Scan();

        String reg = "^*fan";
        Filter filter = new RowFilter(CompareOperator.NOT_EQUAL, new RegexStringComparator(reg));
        scan.setFilter(filter);

        ResultScanner rs = table.getScanner(scan);

        for (Result result : rs) {
            HBaseUtil.printResult(result);
        }
    }

    @Test
    void testFilterList() throws Exception {
        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        Scan scan = new Scan();

        // Default is MUST_PASS_ALL
        // return to null
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        Filter filter1 = new PrefixFilter("yi".getBytes());
        Filter filter2 = new PrefixFilter("zhang".getBytes());
        filterList.addFilter(filter1);
        filterList.addFilter(filter2);

        scan.setFilter(filterList);
        ResultScanner rs = table.getScanner(scan);

        for (Result result : rs) {
            HBaseUtil.printResult(result);
        }
    }

}
