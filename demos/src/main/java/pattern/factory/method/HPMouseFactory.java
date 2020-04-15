package pattern.factory.method;

import pattern.factory.entity.HPMouse;
import pattern.factory.entity.Mouse;

public class HPMouseFactory implements MouseFactory {
    @Override
    public Mouse createMouse() {
        return new HPMouse();
    }
}
