import pk.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        for (String s: args) {
            System.out.println(s);
        }
        System.out.println("Welcome to Piraten Karpen Simulator!");

        //Types of rolls are [MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL] in that order starting from enum 0

        double[] winPercent = playRandom(2);
        System.out.printf("Player 1 wins: %.2f\nPlayer 2 wins: %.2f\n", winPercent[0] * 100, winPercent[1] * 100);

        logger.error("Completed game");
    }

    public static double[] playRandom(int games) {
        //Create 2 players to play the game with the same strategy
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
        }

        //Return an array with the percent of wins for each player as {player1, player2}
        return new double[]{(double)player1Wins / games, (double)player2Wins / games};
    }
}
