package pk;

import java.util.List;

public class CardDrawer extends Drawer{
    public Object draw() {
        return false;
    }

    @Override
    public List<?> draw(int howMany) {
        return null;
    }
}
