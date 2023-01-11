package pk;

import java.util.Arrays;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Player {
    public int playRandom() {
        //This method will play a game with the strategy of randomly rolling dice

        //Creating a new dice to roll and the variable rolledDice to store the results of the rolls
        Dice myDice = new Dice();
        Faces[] rolledDice = myDice.roll8();

        //Keep looping and playing until 3 skulls are rolled
        while (true) {
            //Create a variable to store the number of skulls that exist in the rolls
            int skullCount = 0;

            //This loop goes through the rolls and counts the number of skulls
            for (int roll = 0; roll < 8; roll++) {
                if (rolledDice[roll] == Faces.SKULL) {
                    skullCount++;
                }
            }

            //If the number of skulls rolled is exactly 3, break out of the loop and start to calculate the score
            if (skullCount == 3) {
                break;
            }

            //This loop will go through each dice and randomly choose whether to keep it or not
            for (int roll = 0; roll < 8; roll++) {
                Random bag = new Random();

                //If it has chosen to re-roll the dice, replace that dice roll with a new one
                if (bag.nextInt(2) == 1) {
                    rolledDice[roll] = myDice.roll();
                }
            }
        }

        //Return the score of the player based on the current dice rolls
        return calculateScore(rolledDice);
    }

    private static int calculateScore(Faces[] rolledDice) {
        //This method will calculate the score of a player based on what rolls they currently have.

        //This variable will record the score of the player
        int score = 0;

        //This loop goes through each roll in the recorded rolls, and if the roll is a diamond or a gold coin, we add 100 points to the score
        for (Faces roll: rolledDice) {
            if (roll == Faces.DIAMOND || roll == Faces.GOLD) {
                score += 100;
            }
        }

        return score;
    }
}
