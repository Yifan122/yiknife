package pattern.factory.method;

import pattern.factory.entity.Mouse;

/**
 * 开闭原则
 * 单一原则
 *
 * 但是创建的类过多
 */
public class FactoryMain {
    public static void main(String[] args) {
        MouseFactory mouseFactory = new HPMouseFactory();
        Mouse mouse = mouseFactory.createMouse();
        mouse.sayHi();
    }
}
