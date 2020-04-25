package basic.classloader;

import org.simpleframework.util.ClassUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;

public class MyClassLoader extends ClassLoader {
    private String path;

    MyClassLoader(String path) {
        this.path = path;
    }

    public static void main1(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MyClassLoader myClassLoader = new MyClassLoader("/Users/yifan122/Desktop/javaclass");
        // MyClassLoader's parent is APPClassLoader
        ClassLoader parent = myClassLoader.getParent();
        // APPClassLoader's parent is ExtClassLoader (Extension ClassLoader)
        ClassLoader parent1 = parent.getParent();
        // ExtClassLoader's parent is BootstrapClassLoader (C++ native Object)
        ClassLoader parent2 = parent1.getParent();
        ClassLoader parent3 = parent2.getParent();
        Class<?> yifan = myClassLoader.findClass("Yifan");
        yifan.newInstance();
    }

    public static void main(String[] args) {
        ClassLoader classLoader = ClassUtil.class.getClassLoader();
        URL resource = classLoader.getResource("com/imooc");
        System.out.println(resource.getPath());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = path + "/" + name + ".class";
        Class<?> clazz = null;
        try {
            byte[] bytes = Files.readAllBytes((new File(fileName)).toPath());
            clazz = defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
