package org.simpleframework.util;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {

    public static boolean isEmpty(Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
