package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Player {

    //Creating a new dice to roll and the variable rolledDice to store the results of the rolls
    static Dice myDice = new Dice();
    static Faces[] rolledDice;

    //Random bag variable to randomly roll things
    static Random bag = new Random();

    //This map stores the translation between the cards and their effect on the game
    private static final Map<CardFaces, String[]> cardToGame = Map.of(
            CardFaces.SeaBattle2, new String[]{"SeaBattle", "2"},
            CardFaces.SeaBattle3, new String[] {"SeaBattle", "3"},
            CardFaces.SeaBattle4, new String[] {"SeaBattle", "4"}
    );

    private static final Logger logger = LogManager.getLogger(Player.class);

    private Map<Object, Long> mappingRolls(Faces[] rolledDice) {
        //This method stores the rolls into a mapping of each face and their respective appearance in the rolls

        //Create a mapping of each face to how many times they occur in the rolls
        Map<Object, Long> mapRolls = Arrays.stream(rolledDice).collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        //This will put on the map faces that might not have been on the original rolls. This will ensure there is no null value when getting the key values
        for (Faces roll: Faces.values()) {
            if (!mapRolls.containsKey(roll)) {
                mapRolls.put(roll, 0L);
            }
        }

        //Return the map back
        return mapRolls;
    }
    public int playRandom(Card fortuneCard) {
        //This method will play a game with the strategy of randomly rolling dice

        rolledDice = myDice.roll8();

        Map<Object, Long> mapRolls = mappingRolls(rolledDice);

        //Keep looping and playing until 3 or more skulls are rolled, or until the player wants to keep their rolls
        while (mapRolls.get(Faces.SKULL) < 3) {
            //50% chance the player chooses to keep their current rolls
            if (bag.nextInt(2) == 1) {

                int howManyReRolls = 0;

                //Roll a die. The face that comes up will be the face that I replace in my current rolls. This ensures it is chosen randomly
                Faces die = myDice.roll();

                //Randomly choose how many rerolls to do, based on how many skulls have been rolled.
                //We need a minimum of 2 rolls, which is why I added it after randomly selecting, in case the bag
                //randomly selected 0 or 1. This is also why I have 6 - #skulls, since it will ensure that the number of reroll
                //is not > 8.
                for (int rerolled = 0; rerolled < bag.nextInt(6 - Math.toIntExact(mapRolls.get(Faces.SKULL))) + 2; rerolled++) {

                    //Reroll if it is a skull, or does not currently exist in my rolls. Worst case the expected number of rerolls is 8
                    //and that happens when the 8 rolls are all the same face
                    while (die == Faces.SKULL || mapRolls.get(die) == 0) {
                        die = myDice.roll();
                    }

                    //Remove a face from the mapping value
                    mapRolls.put(die, mapRolls.get(die) - 1);
                    howManyReRolls++;
                }

                for (int roll = 0; roll < howManyReRolls; roll++) {
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

    public int playCombo(Card fortuneCard) {
        //This method will play based on chasing a combo strategy

        rolledDice = myDice.roll8();

        Map<Object, Long> mapRolls = mappingRolls(rolledDice);

        logger.debug("Initial: " + mapRolls);

        //Keep looping and playing until 3 or more skulls are rolled, or until the player wants to keep their rolls
        while (mapRolls.get(Faces.SKULL) < 3) {

            //If I have a face that appears more than 3 times then I do not keep rerolling
            if (Collections.max(mapRolls.values()) < 3) {

                //This variable will turn to true once the program has found the first Face that appears the most amount of times
                //and reroll every face that is not that one
                boolean firstKeyFound = false;
                int howManyReRolls = 0;

                //I loop through every face in the map
                for (Faces roll: Faces.values()) {

                    logger.debug(mapRolls + " 1 " + roll);

                    //If the face is not a skull
                    if (roll != Faces.SKULL) {

                        //I check whether the first key has been found, if it has not and the face appears as many times
                        //as the maximum amount of faces that appears in the rolls then...
                        if (Objects.equals(mapRolls.get(roll), Collections.max(mapRolls.values())) && !firstKeyFound) {

                            //I set the first key found to be true, so that it will reroll all other faces even if they
                            //Have the same number of faces currently
                            firstKeyFound = true;
                            logger.trace(roll);
                        }
                        else if (roll != Faces.DIAMOND && roll != Faces.GOLD){

                            //If the first key has been found, or the face appears less than the maximum amount of faces that appeared
                            //then I keep track of how many dice I need to roll and remove the die from the board
                            //I also keep the roll if it is a diamond or gold
                            howManyReRolls += mapRolls.get(roll);
                            mapRolls.put(roll, 0L);

                        }
                    }
                }

                //If I end up in the rare case of 2 gold, 2 diamond, 2 skulls, and 2 random face and the program designates the random
                //face as the face to keep, it will find 0 to reroll, in that case just end the loop.
                if (howManyReRolls == 0) {
                    break;
                }

                //Reroll however many dice that I removed from the board and add them to the map all at once
                for (int roll = 0; roll < howManyReRolls; roll++) {
                    Faces die = myDice.roll();
                    mapRolls.put(die, mapRolls.get(die) + 1);

                }
            }
            //If the player has chosen to keep the current dice, then we calculate their score
            else {
                break;
            }
        }

        logger.debug(mapRolls + " 2");
        //Return the score of the player based on the current dice rolls
        return calculateScore(mapRolls, fortuneCard);
    }

    public int seaBattle(int sabers, Card fortuneCard){
        //This method is the strategy the players use when they are in a sea battle

        //First I get 8 dice rolls
        rolledDice = myDice.roll8();

        //Map the faces to the rolls
        Map<Object, Long> mapRolls = mappingRolls(rolledDice);

        logger.info("SeaBattle");
        logger.debug(mapRolls);

        //Keep playing the game so long as there is less than 3 skulls, or the number of sabers have been met
        while (mapRolls.get(Faces.SKULL) < 3 && mapRolls.get(Faces.SABER) < sabers) {
            int howManyReRolls = 0;

            //For every face possible
            for (Faces roll: Faces.values()) {

                logger.debug(mapRolls + " 1 " + sabers);

                //If the face is not a skull or a saber, if they are then do not touch them
                if (roll != Faces.SKULL && roll != Faces.SABER) {

                    //Then I remove those die from the board and into my "hand" and set the number of those die on the map to 0
                    //This removes the possibility that I reroll the same die twice and count it as 2 rolls in the same turn
                    howManyReRolls += mapRolls.get(roll);
                    mapRolls.put(roll, 0L);
                }
            }
            //I reroll as many as indicated and add the results to the map
            for (int roll = 0; roll < howManyReRolls; roll++) {
                Faces die = myDice.roll();
                mapRolls.put(die, mapRolls.get(die) + 1);
            }
        }

        //Return the calculated score of the dice rolls along with the fortune card
        logger.debug(mapRolls + " 2");
        return calculateScore(mapRolls, fortuneCard);
    }
    private static int calculateScore(Map<Object, Long> rolled, Card fortuneCard) {
        //This method will calculate the score of a player based on what rolls they currently have.

        //This variable will record the score of the player, and fullChestCheck will record how many dice are being used to generate scores
        int score = 0;
        int fullChestCheck = 0;

        //First check if there are 3 skulls in the rolls
        if (rolled.get(Faces.SKULL) >= 3) {

            //If there are, then check whether or not the players had a sea battle this round
            if (cardToGame.containsKey(fortuneCard.face) && Objects.equals(cardToGame.get(fortuneCard.face)[0], "SeaBattle")) {

                //Then check if the number of sabers rolled matches the number of sabers needed for the sea battle
                if (rolled.get(Faces.SABER) == Integer.parseInt(cardToGame.get(fortuneCard.face)[1])) {
                    logger.debug("Score: " + score);

                    //If they do, just return the score
                    return score;
                }

                //If the # of saber rolls do not match the number needed, then subtract the score
                logger.debug("Score: " + (score - fortuneCard.points));
                return score - fortuneCard.points;
            }

            //If the player was not in a sea battle, then just reuturn the score of 0
            logger.debug("Score: " + score);
            return score;
        }

        //Another scenario is that the player was in a sea battle and won, in which case they get the amount of points on the card
        else if (cardToGame.containsKey(fortuneCard.face) && Objects.equals(cardToGame.get(fortuneCard.face)[0], "SeaBattle")){
            score += fortuneCard.points;
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
