package pk;

import java.util.Random;

public class Player {
    private int play() {
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
                //randomize rolls and which one to keep
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
