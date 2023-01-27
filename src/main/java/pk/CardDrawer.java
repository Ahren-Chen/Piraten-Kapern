package pk;

import java.util.ArrayList;
import java.util.List;

public class CardDrawer extends Drawer<Card>{
    static CardPile pile = new CardPile();

    @Override
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

    public void shuffle() {
        pile.shuffle();
    }
}
