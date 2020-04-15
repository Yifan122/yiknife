package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonInfoAnnotation {

    /**
     * annotation 中的属性用方法来表示
     * 并且修饰符只能为public
     */
    String name();

    int age();

    String gender() default "male";
}
