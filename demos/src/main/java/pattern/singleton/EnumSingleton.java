package pattern.singleton;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumSingleton {
    public static EnumSingleton getInstance() {
        return Container.HOLD.instance;
    }

    private enum Container {
        HOLD;
        EnumSingleton instance = Container();

        private EnumSingleton Container() {
            System.out.println("create");
            return new EnumSingleton();
        }
    }
}
