package guava;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class IOdemo {
    @Test
    public void copy() throws IOException {
        ByteSource source = Files.asByteSource(new File(""));
        ByteSink sink = Files.asByteSink(new File(""));

        source.copyTo(sink);
    }
}
