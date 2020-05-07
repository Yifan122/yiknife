package com.bigdata.hbase.util;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
    /**
     * Formatted print result from HBase
     *
     * @param result
     */
    public static void printResult(Result result) {
        for (Cell cell : result.rawCells()) {
            System.out.println(Bytes.toString(result.getRow()) + " \t"
                    + Bytes.toString(CellUtil.cloneFamily(cell)) + "\t"
                    + Bytes.toString(CellUtil.cloneQualifier(cell)) + "\t"
                    + Bytes.toString(CellUtil.cloneValue(cell))
            );
        }
    }
}
