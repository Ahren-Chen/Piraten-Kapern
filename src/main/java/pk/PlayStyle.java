package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

public class PlayStyle {
    private static final Logger logger = LogManager.getLogger(PlayStyle.class);

    private static final Map<CardFaces, String[]> cardToGameplay = Map.of(
            CardFaces.NOP, new String[] {"N/A"},
            CardFaces.SeaBattle2, new String[] {"SeaBattle", "2"},
            CardFaces.SeaBattle3, new String[] {"SeaBattle", "3"},
            CardFaces.SeaBattle4, new String[] {"SeaBattle", "4"},
            CardFaces.MonkeyBusiness, new String[] {"N/A"}
    );
    static CardDrawer drawer = new CardDrawer();

    public static double[] play(int games, int randomPlayers) {
        //Create 2 new players
        //If there is only 1 person playing using the combo strategy, we will assume player 1 plays randomly
        Player player1 = new Player();
        Player player2 = new Player();

        //Create 2 variables to store the number of wins each player has after playing
        int player1Wins = 0;
        int player2Wins = 0;

        //Loop through the number of games the user wants to simulate
        for (int game = 0; game < games; game++) {
            //Create variables for calculating the score that is returned from the player playing
            int score1 = 0;
            int score2 = 0;
            int winningScore = 6000;

            //Have a loop that keeps playing the game so long as both players have not yet reached 6000 points
            while (score1 < winningScore && score2 < winningScore) {

                //At the start of the round, draw a card for each player
                Card[] fortuneCards = {drawer.draw(), drawer.draw()};

                //Check whether the first player is in a sea battle
                if (Objects.equals(cardToGameplay.get(fortuneCards[0].face)[0], "SeaBattle")) {

                    //If the first player is, then calculate the number of sabers needed and have the first player play a sea battle
                    int sabersNeeded = Integer.parseInt(cardToGameplay.get(fortuneCards[0].face)[1]);
                    score1 += player1.seaBattle(sabersNeeded, fortuneCards[0]);

                    //Then check whether the second player is in a sea battle
                    if (Objects.equals(cardToGameplay.get(fortuneCards[1].face)[0], "SeaBattle")) {

                        //If they are, then also play a sea battle with a the calculated amount of sabers needed
                        sabersNeeded = Integer.parseInt(cardToGameplay.get(fortuneCards[1].face)[1]);
                        score2 += player2.seaBattle(sabersNeeded, fortuneCards[1]);
                    }

                    //If they are not part of a sea battle, then check how many random players there are.
                    //If both players are random, then play random
                    else if (randomPlayers == 2) {
                        score2 += player2.playRandom(fortuneCards[1]);
                    }

                    //Otherwise play combo, since the game is set up so that the first player always plays random and the second player always plays combo
                    //If there is one of each
                    else {
                        score2 += player2.playCombo(fortuneCards[1]);
                    }
                }

                //Check whether the second player is in a sea battle or not
                else if (Objects.equals(cardToGameplay.get(fortuneCards[1].face)[0], "SeaBattle")) {

                    //If they are, then calculate the number of sabers needed and play a sea battle
                    int sabersNeeded = Integer.parseInt(cardToGameplay.get(fortuneCards[1].face)[1]);
                    score2 += player2.seaBattle(sabersNeeded, fortuneCards[1]);

                    //Check whether both players are playing combo, if they are then the first player plays combo.
                    if (randomPlayers == 0) {
                        score1 += player1.playCombo(fortuneCards[0]);
                    }

                    //Otherwise play random, since the first player always plays random if there is at least 1 random player
                    else {
                        score1 += player1.playRandom(fortuneCards[0]);
                    }
                }

                //If both players are playing random
                else if (randomPlayers == 2) {
                    score1 += player1.playRandom(fortuneCards[0]);
                    score2 += player2.playRandom(fortuneCards[1]);
                }

                //If only 1 player is playing randomly
                else if (randomPlayers == 1) {
                    score1 += player1.playRandom(fortuneCards[0]);
                    score2 += player2.playCombo(fortuneCards[1]);
                }

                //If both players are playing combo
                else {
                    score1 += player1.playCombo(fortuneCards[0]);
                    score2 += player2.playCombo(fortuneCards[1]);
                }
            }

            //After each game, shuffle the deck to a fresh one
            logger.info("Game end");
            drawer.shuffle();

            //Based on what score is bigger, we add a win to the respective player. If the score is equal, no player wins
            if (score1 > score2) {
                player1Wins++;
            }
            else if (score2 > score1) {
                player2Wins++;
            }
            logger.trace("Player 1 Score: " + score1 + ", Wins: " + player1Wins);
            logger.trace("Player 2 Score: " + score2 + ", Wins: " + player2Wins);
        }

        //Return an array with the percent of wins for each player as {player1, player2}
        return new double[]{(double)player1Wins / games, (double)player2Wins / games};
    }
}
