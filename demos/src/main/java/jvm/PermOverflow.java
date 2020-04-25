package jvm;

import java.util.Random;

/**
 * Use JDK 6
 * String intern will put to the constant pool
 * config: -XX:MaxPermSize=6M -XX:PermSize=6M
 */
public class PermOverflow {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            randomString(1000000).intern();
        }
        System.out.println("Mission Complete");
    }

    private static String randomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
}
