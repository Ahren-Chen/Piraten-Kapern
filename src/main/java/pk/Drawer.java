package pk;

import java.util.List;

abstract public class Drawer<T> {
    public abstract T draw();
    public abstract List<?> draw(int howMany);
}
