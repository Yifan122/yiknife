package lambda.Stream;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流创建的四种方式
 */
public class StreamConstructor {
    @Test
    public void createFromConstant() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        stream.forEach(System.out::println);
    }

    @Test
    public void createFromArray() {
        int[] arr = new int[]{1, 2, 3, 5};
        IntStream stream = Arrays.stream(arr);
        stream.forEach(System.out::println);
    }

    @Test
    public void createFromFile() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("/Users/yifan122/Desktop/workspace/yiknife/demos/src/main/java/lambda/Stream/StreamConstructor.java"));
        stream.forEach(System.out::println);
    }

    @Test
    public void createFromFunction() {
        Stream<Double> generate = Stream.generate(Math::random);
        generate.limit(100).forEach(System.out::println);
    }
}
