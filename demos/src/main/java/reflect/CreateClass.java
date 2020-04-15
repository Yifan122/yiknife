package reflect;

public class CreateClass {
    public static void main(String[] args) throws ClassNotFoundException {
        TargetClass targetClass = new TargetClass();

        // 3 methods to get TargetClass Class object
        Class target1 = TargetClass.class;

        Class target2 = targetClass.getClass();

        Class<?> target3 = Class.forName("reflect.TargetClass");

        // 运行期间只有一个Class对象产生
        System.out.println(target1 == target2);
        System.out.println(target2 == target3 );
    }
}
