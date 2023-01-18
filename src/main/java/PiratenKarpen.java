import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import pk.Player;
import pk.PlayStyle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("Incorrect input arguments. Please enter enter input argument 'trace' or 'normal'");
            return;
        }
        if (Objects.equals(args[0], "normal")) {
            Logger logger = LogManager.getRootLogger();
            Configurator.setAllLevels(logger.getName(), Level.getLevel("ERROR"));
        } else if (!Objects.equals(args[0], "trace")){
            logger.error("Incorrect usage. Please enter input argument 'trace' or 'normal'");
            return;
        }
        logger.info("Game starts: ");
        System.out.println("Welcome to Piraten Karpen Simulator!");

        //Types of rolls are [MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL] in that order starting from enum 0

        logger.info("Begin to play 42 games");
        Player player1 = new Player();
        Player player2 = new Player();
        double[] winPercent = PlayStyle.playRandom(player1, player2, 42);
        System.out.printf("Player 1 wins: %.2f\nPlayer 2 wins: %.2f\n", winPercent[0] * 100, winPercent[1] * 100);

        logger.info("Completed game");
    }
}
