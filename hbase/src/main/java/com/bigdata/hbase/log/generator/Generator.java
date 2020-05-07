package com.bigdata.hbase.log.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
public class Generator {
    private final static String LOG_NAME = "logname.txt";
    private final static String IP_PREFIX = "192.168.";
    private final static String[] WEB_LIST = {"https://coding.imooc.com/", "https://mvnrepository.com/", "https://www.google.com/"};
    private final static Random random = new Random();
    private static String[] logName;

    static {
        try (InputStream inputStream =
                     Thread.currentThread().getContextClassLoader().getResourceAsStream(LOG_NAME)) {
            logName = IOUtils.toString(inputStream, StandardCharsets.UTF_8).split("\n");
        } catch (Exception e) {
            log.error("Cannot find the file {}.", "logname.txt");
        }
    }

    public static void main(String[] args) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("/Users/yifan/Desktop/workspace/yiknife/hbase/src/main/resources/access.log")))) {
            for (int i = 0; i < 1000000; i++) {
                bufferedWriter.write(generatorRecord());
            }
        } catch (Exception e) {
            log.error("Write error");
        }

        System.out.println("Finished");
    }

    // ip + url + data
    private static String generatorRecord() {
        StringBuilder sb = new StringBuilder();
        int ip1 = random.nextInt(15) + 10;
        int ip2 = random.nextInt(100) + 128;

        String web = WEB_LIST[random.nextInt(WEB_LIST.length)];
        String file = logName[random.nextInt(logName.length)];

        int data = random.nextInt(1000);

        sb.append(IP_PREFIX)
                .append(ip1)
                .append(".")
                .append(ip2)
                .append("\t")
                .append(web)
                .append(file)
                .append("\t")
                .append(data)
                .append("\n");

        return sb.toString();
    }


}
