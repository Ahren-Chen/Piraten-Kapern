package pk;

import java.util.ArrayList;
import java.util.List;

public class CardDrawer extends Drawer<Card>{
    CardPile pile = new CardPile();
    public Card draw() {
        return pile.draw();

    }

    @Override
    public List<?> draw(int howMany) {
        List<Card> Draws = new ArrayList<>();
        for (int card = 0; card < howMany; card++) {
            Draws.add(pile.draw());
        }
        return Draws;
    }
}
