package pattern.singleton;

public class LazySingleton {
    private static volatile LazySingleton instance = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    // volatile 的作用 (防止重排）
                    // 1. allocate(memory)
                    // 2. new instance
                    // 3. point to memory
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
