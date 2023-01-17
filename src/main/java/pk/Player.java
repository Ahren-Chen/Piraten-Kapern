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
        Random bag = new Random();

        //Create a variable to store the number of skulls that exist in the rolls
        int skullCount = 0;

        //This loop goes through the rolls and counts the number of skulls
        for (Faces roll: rolledDice) {
            if (roll == Faces.SKULL) {
                skullCount++;
            }
        }

        //Keep looping and playing until 3 or more skulls are rolled
        while (skullCount < 3) {

            if (bag.nextInt(2) == 1) {
                //This loop will go through each dice and randomly choose whether to keep it or not as long as it is not a skull
                for (int roll = 0; roll < 8; roll++) {
                    //If it has chosen to re-roll the dice, replace that dice roll with a new one so long as the dice is not a skull
                    if (bag.nextInt(2) == 1 && rolledDice[roll] != Faces.SKULL) {
                        rolledDice[roll] = myDice.roll();

                        //If the newly rolled dice is a skull, add it to the skull count
                        if (rolledDice[roll] == Faces.SKULL) {
                            skullCount++;
                        }
                    }
                }
            }
            //If the player has chosen to keep the current dice, then we calculate their score
            else {
                break;
            }
        }

        //Return the score of the player based on the current dice rolls
        return calculateScore(rolledDice, skullCount);
    }

    private static int calculateScore(Faces[] rolledDice, int skullCount) {
        //This method will calculate the score of a player based on what rolls they currently have.
        if (skullCount >= 3) {
            return 0;
        }

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
