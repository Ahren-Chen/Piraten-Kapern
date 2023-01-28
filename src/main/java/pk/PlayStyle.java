package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

public class PlayStyle {
    private static final Logger logger = LogManager.getLogger(PlayStyle.class);

    private static final Map<CardFaces, String[]> cardToGameplay = Map.of(
            CardFaces.SeaBattle2, new String[] {"SeaBattle", "2"},
            CardFaces.SeaBattle3, new String[] {"SeaBattle", "3"},
            CardFaces.SeaBattle4, new String[] {"SeaBattle", "4"}
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
                Card fortuneCard = drawer.draw();

                if (cardToGameplay.containsKey(fortuneCard.face) && Objects.equals(cardToGameplay.get(fortuneCard.face)[0], "SeaBattle")) {
                    int sabersNeeded = Integer.parseInt(cardToGameplay.get(fortuneCard.face)[1]);
                    score1 += player1.seaBattle(sabersNeeded, fortuneCard);
                    score2 += player2.seaBattle(sabersNeeded, fortuneCard);
                }
                else if (randomPlayers == 2) {
                    score1 += player1.playRandom(fortuneCard);
                    score2 += player2.playRandom(fortuneCard);
                }
                else if (randomPlayers == 1) {
                    score1 += player1.playRandom(fortuneCard);
                    score2 += player2.playCombo(fortuneCard);
                }
                else {
                    score1 += player1.playCombo(fortuneCard);
                    score2 += player2.playCombo(fortuneCard);
                }
            }

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
