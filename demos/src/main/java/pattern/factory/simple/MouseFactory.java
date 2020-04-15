package pattern.factory.simple;

import pattern.factory.entity.HPMouse;
import pattern.factory.entity.LenovoMouse;
import pattern.factory.entity.Mouse;

import java.util.ArrayList;
import java.util.List;

/**
 * 适合需要创建的对象比较少
 * 代码会比较臃肿
 * 不适合维护
 *
 * 优点：比较简单
 *
**/
public class MouseFactory {
    public static Mouse createMouse(int type) {
        Mouse mouse = null;

        switch (type) {
            case 2: return new HPMouse();
            default: return new LenovoMouse();
        }
    }

    public static void main(String[] args) {
        List<Mouse> mouseList = new ArrayList<>();
        Mouse lenoveMouse = createMouse(1);
        Mouse hpMouse = createMouse(2);

        mouseList.add(lenoveMouse);
        mouseList.add(hpMouse);

        for (Mouse mouse : mouseList) {
            mouse.sayHi();
        }

    }
}
