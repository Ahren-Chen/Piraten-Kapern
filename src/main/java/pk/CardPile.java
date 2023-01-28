package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CardPile implements Source<Card> {

    //Logger
    private static final Logger logger = LogManager.getLogger(CardPile.class);

    //This will store all the cards in a list, I chose this over a stack of a queue because it makes it easier to randomly replace
    //the NOP cards with randomly places regular cards
    static List<Card> Cards = new ArrayList<>();

    //This is a map of all the cards that I am using and map them to how many of them are in the current deck
    static Map<Card, Integer> CardsLeft = Map.of (
            new Card(CardFaces.NOP, 0), 29,
            new Card(CardFaces.SeaBattle2, 200), 2,
            new Card(CardFaces.SeaBattle3, 500), 2,
            new Card(CardFaces.SeaBattle4, 1000), 2
    );

    //Random variable
    static Random bag = new Random();

    public CardPile() {
        //When I create my card pile I want to shuffle the deck
        shuffle();
    }
    @Override
    public boolean isEmpty() {
        return Cards.isEmpty();
    }

    @Override
    public Card draw() {
        //This method draws the card from the pile and returns it

        //I check if the pile is empty, if it is then I shuffle everything for a new pile
        if (isEmpty()) {
            logger.info("Empty");
            shuffle();
        }

        //I get the top most card and return it, and remove it from the pile
        Card drawnCard = Cards.get(0);
        Cards.remove(0);
        return drawnCard;
    }

    public void shuffle() {
        //This method shuffles the desk

        //First I check if the pile is empty, if it is not then I empty it
        if (!isEmpty()) {
            Cards.clear();
        }

        //I fill the card pile will NOP cards
        for (int card = 0; card < 35; card ++) {
            Cards.add(new Card(CardFaces.NOP, 0));
        }

        //I go through each key or card in the map
        for (Card key: CardsLeft.keySet()) {

            //If the key is a non NOP card, then do the following
            if (key.face != CardFaces.NOP) {

                //I loop through as many cards as needed to input all the cards into the deck
                for (int numberOfCards = 0; numberOfCards < CardsLeft.get(key); numberOfCards++) {

                    //I place them in a random index from [0, 35)
                    int cardSpace = bag.nextInt(Cards.size());

                    //Keep choosing a random index until you find a NOP card to replace instead of one that is important
                    while (Cards.get(cardSpace).face != CardFaces.NOP) {
                        cardSpace = bag.nextInt(Cards.size());
                    }

                    //Replace the NOP card with the desired card
                    Cards.set(cardSpace, key);
                }
            }
        }
    }
}
