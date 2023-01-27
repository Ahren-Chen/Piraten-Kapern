package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    //Creating a new dice to roll and the variable rolledDice to store the results of the rolls
    static Dice myDice = new Dice();
    static Faces[] rolledDice;
    static Random bag = new Random();

    private static final Logger logger = LogManager.getLogger(Player.class);
    public int playRandom(CardDrawer drawer) {
        //This method will play a game with the strategy of randomly rolling dice

        rolledDice = myDice.roll8();
        Card fortuneCard = drawer.draw();

        //Create a mapping of each face to how many times they occur in the rolls
        Map<Object, Long> mapRolls = Arrays.stream(rolledDice).collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        //This will put on the map faces that might not have been on the original rolls. This will ensure there is no null value when getting the key values
        for (Faces roll: Faces.values()) {
            if (!mapRolls.containsKey(roll)) {
                mapRolls.put(roll, 0L);
            }
        }

        //Keep looping and playing until 3 or more skulls are rolled, or until the player wants to keep their rolls
        while (mapRolls.get(Faces.SKULL) < 3) {
            //50% chance the player chooses to keep their current rolls
            if (bag.nextInt(2) == 1) {

                //Randomly choose how many rerolls to do, based on how many skulls have been rolled.
                //We need a minimum of 2 rolls, which is why I added it after randomly selecting, in case the bag
                //randomly selected 0 or 1. This is also why I have 6 - #skulls, since it will ensure that the number of reroll
                //is not > 8.
                for (int rerolled = 0; rerolled < bag.nextInt(6 - Math.toIntExact(mapRolls.get(Faces.SKULL))) + 2; rerolled++) {

                    //Roll a die. The face that comes up will be the face that I replace in my current rolls. This ensures it is chosen randomly
                    Faces die = myDice.roll();

                    //Reroll if it is a skull, or does not currently exist in my rolls. Worst case the expected number of rerolls is 8
                    //and that happens when the 8 rolls are all the same face
                    while (die == Faces.SKULL || mapRolls.get(die) == 0) {
                        die = myDice.roll();
                    }

                    //Remove a face from the mapping value
                    mapRolls.put(die, mapRolls.get(die) - 1);

                    //Actual reroll, then map an extra face to whatever the outcome is.
                    die = myDice.roll();
                    mapRolls.put(die, mapRolls.get(die) + 1);
                }
            }
            //If the player has chosen to keep the current dice, then we calculate their score
            else {
                break;
            }
        }

        //Return the score of the player based on the current dice rolls
        return calculateScore(mapRolls, fortuneCard);
    }

    public int playCombo(CardDrawer drawer) {
        //This method will play based on chasing a combo strategy

        rolledDice = myDice.roll8();
        Card fortuneCard = drawer.draw();

        //Create a mapping of each face to how many times they occur in the rolls
        Map<Object, Long> mapRolls = Arrays.stream(rolledDice).collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        //This will put on the map faces that might not have been on the original rolls. This will ensure there is no null value when getting the key values
        for (Faces roll: Faces.values()) {
            if (!mapRolls.containsKey(roll)) {
                mapRolls.put(roll, 0L);
            }
        }
        logger.debug(mapRolls);

        //Keep looping and playing until 3 or more skulls are rolled, or until the player wants to keep their rolls
        while (mapRolls.get(Faces.SKULL) < 3) {
            //logger.debug(mapRolls);
            //If I have a face that appears more than 3 times then I do not keep rerolling
            if (Collections.max(mapRolls.values()) < 3) {
                boolean firstKeyFound = false;
                for (Faces roll: Faces.values()) {
                    logger.debug(mapRolls);
                    if (roll != Faces.SKULL) {
                        if (Objects.equals(mapRolls.get(roll), Collections.max(mapRolls.values())) && !firstKeyFound) {
                            firstKeyFound = true;
                            logger.trace(roll);
                        }
                        else {
                            for (int rerolled = 0; rerolled < mapRolls.get(roll); rerolled++) {
                                Faces die = myDice.roll();
                                mapRolls.put(die, mapRolls.get(die) + 1);
                            }
                            mapRolls.put(roll, 0L);

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
        return calculateScore(mapRolls, fortuneCard);
    }
    private static int calculateScore(Map<Object, Long> rolled, Card fortuneCard) {
        //This method will calculate the score of a player based on what rolls they currently have.

        //This variable will record the score of the player, and fullChestCheck will record how many dice are being used to generate scores
        int score = 0;
        int fullChestCheck = 0;

        score += fortuneCard.points;

        if (rolled.get(Faces.SKULL) >= 3) {
            return 0;
        }

        //The score is based on how many gold coins and diamonds the player has rolled
        score += (rolled.get(Faces.GOLD) + rolled.get(Faces.DIAMOND)) * 100;

        //I create a map that maps every possible number of identical face rolls to their desired points
        Map<Integer, Integer> comboScoreboard = Map.of(
                0, 0,
                1, 0,
                2, 0,
                3, 100,
                4, 200,
                5, 500,
                6, 1000,
                7, 2000,
                8, 4000
        );

        //For each face, check if any of them will have a combo bonus and add it to the score. 3 Skulls will never be counted as a bonus
        //because it wil return before it reaches this stage as seen by the return statement above
        for (Faces roll: Faces.values()) {
            //I get the value from the scoreBoard using the number of faces that show up in my rolls as the key and store that in a variable
            int faceScore = comboScoreboard.get(
                    Math.toIntExact(
                            rolled.get(roll)));

            //If the face is used to generate any kind of score, then add the number of rolls to the fullChestCheck variable
            if (faceScore != 0 || roll == Faces.GOLD || roll == Faces.DIAMOND) {
                fullChestCheck += rolled.get(roll);
            }

            score += faceScore;
        }

        //If all 8 dice are used to generate points, add 500 points to the score
        if (fullChestCheck == 8) {
            score += 500;
        }

        logger.debug("Score: " + score);
        return score;
    }
}
