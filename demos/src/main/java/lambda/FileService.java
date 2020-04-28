package lambda;

import lambda.predicate.StringPredicate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileService {
    public static void fileHandler(String url, StringPredicate stringPredicate) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(url)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 处理业务逻辑
                line = stringPredicate.handler(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String upCase(String string) {
        return string.toUpperCase();
    }

    public static void main(String[] args) {
//        // 改成大写
//        FileService.fileHandler("/Users/yifan122/Desktop/workspace/yiknife/demos/src/main/java/lambda/predicate/StringPredicate.java",
//                str -> str.toUpperCase());
//
//        // 计算每行的字数
//        FileService.fileHandler("/Users/yifan122/Desktop/workspace/yiknife/demos/src/main/java/lambda/predicate/StringPredicate.java",
//                str -> {
//                    System.out.println(str.length());
//                    return str.replace("public", "private");
//                });

        // ClassName :: static Method
        FileService.fileHandler("/Users/yifan122/Desktop/workspace/yiknife/demos/src/main/java/lambda/predicate/StringPredicate.java",
                FileService::upCase);
    }
}
