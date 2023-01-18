package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayStyle {
    private static final Logger logger = LogManager.getLogger(PlayStyle.class);

    public static double[] playRandom(Player player1, Player player2, int games) {
        //Create 2 variables to store the number of wins each player has after playing
        int player1Wins = 0;
        int player2Wins = 0;

        //Loop through the number of games the user wants to simulate
        for (int game = 0; game < games; game++) {
            //Create variables for calculating the score that is returned from the player playing
            int score1 = 0;
            int score2 = 0;

            //Have a loop that keeps playing the game so long as both players have not yet reached 6000 points
            while (score1 < 6000 && score2 < 6000) {
                score1 += player1.playRandom();
                score2 += player2.playRandom();
            }

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
