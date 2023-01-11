package pk;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Player {
    public int play() {
        Dice myDice = new Dice();
        Faces[] rolledDice = myDice.roll8();
        while (true) {
            int skullCount = 0;
            for (int roll = 0; roll < 8; roll++) {
                if (rolledDice[roll] == Faces.SKULL) {
                    skullCount++;
                }
            }
            if (skullCount == 3) {
                break;
            }

            for (int roll = 0; roll < 8; roll++) {
                Random bag = new Random();
                if (bag.nextInt(2) == 1) {
                    rolledDice[roll] = myDice.roll();
                }
            }
        }

        return calculateScore(rolledDice);
    }

    private static int calculateScore(Faces[] rolledDice) {
        int score = 0;
        for (Faces roll: rolledDice) {
            if (roll == Faces.DIAMOND || roll == Faces.GOLD) {
                score += 100;
            }
        }
        return score;
    }
}
