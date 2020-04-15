package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationDemo {
    public static void main(String[] args) throws NoSuchFieldException {
        ImoocCourse imoocCourse = new ImoocCourse();

        Class<? extends ImoocCourse> imoocCourseClass = imoocCourse.getClass();

        // Class 的注解
        Annotation[] imoocCourseClassAnnotations = imoocCourseClass.getAnnotations();
        for (Annotation annotation : imoocCourseClassAnnotations) {
            System.out.println(annotation.toString());
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
            System.out.println(courseInfoAnnotation.name());
            System.out.println(courseInfoAnnotation.language());
        }

        // 获得属性的注解
        Field courseClassField = imoocCourseClass.getDeclaredField("author");
        Annotation[] classFieldAnnotations = courseClassField.getAnnotations();
        for (Annotation annotation : classFieldAnnotations) {
            System.out.println(annotation.toString());
        }
    }
}
