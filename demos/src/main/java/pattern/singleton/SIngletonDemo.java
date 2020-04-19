package pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SIngletonDemo {
    public static void lazySingletonDemo() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(() -> System.out.println(LazySingleton.getInstance()));
        }
    }

    public static void reflectionInvasionLazySingleTon() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LazySingleton origin = LazySingleton.getInstance();
        System.out.println(origin);

        // Use reflection to create new instance
        Class<?> clazz = LazySingleton.class;
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        LazySingleton secondSingleton = (LazySingleton) constructor.newInstance();
        System.out.println(secondSingleton);

        if (origin != secondSingleton) {
            System.out.println("The two instances are different");
        }
    }

    public static void enumSingletonDemo() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> System.out.println(EnumSingleton.getInstance()));
        }
    }

    public static void reflectionInvasionEnumSingleTon() throws NoSuchFieldException {
        Class<?> enumClass = EnumSingleton.class;
        Field container = enumClass.getField("Container");
        System.out.println(container);
    }


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
//        lazySingletonDemo();
//        reflectionInvasion();

//        enumSingletonDemo();
//        reflectionInvasionEnumSingleTon();
    }

}
