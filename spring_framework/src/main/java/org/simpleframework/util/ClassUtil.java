package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {

    private static final String FILE_PROTOCOL = "file";

    /**
     * Get the Class Object in the given packageName
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        // 1. Get the ClassLoader -> ClassLoader can get the relative Path
        ClassLoader classLoader = getClassLoader();

        // 2. Using the ClassLoader to load the resources URL we need
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("Unable to retrieve anything from package: " + packageName);
            return null;
        }

        // 3. load Class
        Set<Class<?>> classSet = new HashSet<>();
        if (url.getProtocol().equals(FILE_PROTOCOL)) {
            loadClassInDirectory(classSet, new File(url.getFile()), packageName);
        }
        return classSet;
    }

    /**
     * Load all class into a set
     *
     * @param classSet
     * @param file
     * @param packageName
     */
    public static void loadClassInDirectory(Set<Class<?>> classSet, File file, String packageName) {
        if (file.isFile()) {
            String absolutePath = file.getAbsolutePath().replace(File.separator, ".");
            String classPath = absolutePath.substring(absolutePath.indexOf(packageName), absolutePath.lastIndexOf("."));
            classSet.add(loadClass(classPath));
            return;
        }

        File[] classes = file.listFiles(ClassUtil::accept);

        if (classes == null)
            return;

        for (File aClass : classes) {
            loadClassInDirectory(classSet, aClass, packageName);
        }
    }

    /**
     * Create new instance
     *
     * @param clazz
     * @param accessible create an object using its private constructor
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            log.error("No NoArgsConstructor in " + clazz.getName());
            e.printStackTrace();
        }

        constructor.setAccessible(accessible);
        try {
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("Cannot create new instance");
            throw new RuntimeException("New Instance create fail");
        }
    }

    /**
     * Load class
     *
     * @param className
     * @return
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("Class is not found" + className);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Get the GlassLoader
     *
     * @return
     */
    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Filter the file need to load
     *
     * @param pathname
     * @return
     */
    private static boolean accept(File pathname) {
        return pathname.isDirectory() || pathname.getName().endsWith("class");
    }
}
