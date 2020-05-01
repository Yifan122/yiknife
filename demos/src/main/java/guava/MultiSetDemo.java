package guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MultiSetDemo {
    final private String text = "Crédit Agricole CIB in Singapore provides a wide range of corporate and investment banking products and services, " +
            "fixed income and structured finance solutions including structured trade finance facilities to international commodity traders, producers, " +
            "refiners and importers.\n" +
            "\n" +
            "Crédit Agricole CIB strongly supports the status of Singapore as a leading regional oil and commodity hub. The main counterparts and " +
            "clients of its Fixed Income Markets and Structured Finance business lines are based in Singapore and South-East Asia, and include " +
            "corporates and financial institutions.";

    @Test
    public void count() {
        Multiset<String> multiset = HashMultiset.create();
        String[] strs = text.split(" ");

        Arrays.stream(strs)
                .forEach(str -> multiset.add(str));

        System.out.println(multiset.size());
        System.out.println(multiset.count("CIB"));

    }
}
