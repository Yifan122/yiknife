package pattern.factory.method;

import pattern.factory.entity.LenovoMouse;
import pattern.factory.entity.Mouse;

public class LenovoMouseFactory implements MouseFactory {
    @Override
    public Mouse createMouse() {
        return new LenovoMouse();
    }
}
