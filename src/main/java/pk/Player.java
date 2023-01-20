package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Player {

    private static final Logger logger = LogManager.getLogger(Player.class);
    public int playRandom() {
        //This method will play a game with the strategy of randomly rolling dice

        //Creating a new dice to roll and the variable rolledDice to store the results of the rolls
        Dice myDice = new Dice();
        Faces[] rolledDice = myDice.roll8();
        Random bag = new Random();
        logger.debug("Initial roll: " + Arrays.toString(rolledDice));

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
            int rerolled = 0;

            if (bag.nextInt(2) == 1) {
                //This loop will go through each dice and randomly choose whether to keep it or not as long as it is not a skull
                for (int roll = 0; roll < rolledDice.length; roll++) {
                    if (rolledDice[roll] != Faces.SKULL) {
                        //If it has chosen to re-roll the dice, replace that dice roll with a new one so long as the dice is not a skull
                        //It must also reroll at least 2 die.
                        if (rerolled < 2 || bag.nextInt(2) == 1) {
                            rolledDice[roll] = myDice.roll();
                            rerolled++;
                            if (rolledDice[roll] == Faces.SKULL) {
                                skullCount++;
                            }
                        }
                    }
                }
            }
            //If the player has chosen to keep the current dice, then we calculate their score
            else {
                break;
            }
        }

        logger.debug("Final rolls: " + Arrays.toString(rolledDice));

        Map<Object, Long> mapRoll = Arrays.stream(rolledDice).collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        //Return the score of the player based on the current dice rolls
        return calculateScore(mapRoll, skullCount);
    }

    private static int calculateScore(Map<Object, Long> rolledDice, int skullCount) {
        //This method will calculate the score of a player based on what rolls they currently have.
        if (skullCount >= 3) {
            return 0;
        }

        //This variable will record the score of the player
        int score = 0;

        //The score is based on how many gold coins and diamonds the player has rolled
        if (rolledDice.containsKey(Faces.GOLD)) {
            score += rolledDice.get(Faces.GOLD) * 100;
        }
        if (rolledDice.containsKey(Faces.DIAMOND)) {
            score += rolledDice.get(Faces.DIAMOND) * 100;
        }

        logger.debug("Score: " + score);
        return score;
    }
}
