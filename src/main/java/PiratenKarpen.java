import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import pk.PlayStyle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        if (args.length != 3) {
            logger.error("Incorrect input arguments. Please enter mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args=\"[trace/normal] [random/combo] [random/combo]\"");
            return;
        }
        if (Objects.equals(args[0].toLowerCase(), "normal")) {
            Logger logger = LogManager.getRootLogger();
            Configurator.setAllLevels(logger.getName(), Level.getLevel("ERROR"));
        }
        else if (!Objects.equals(args[0].toLowerCase(), "trace")){
            logger.error("Incorrect usage. Please enter mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args=\"[trace/normal] [random/combo] [random/combo]\"");
            return;
        }

        int randomPlayers = 0;
        for (int arg = 1; arg < args.length; arg++) {
            if (Objects.equals(args[arg].toLowerCase(), "random")) {
                randomPlayers++;
            }
            else if (!Objects.equals(args[arg].toLowerCase(), "combo")) {
                logger.error("Incorrect usage. Please enter mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args=\"[trace/normal] [random/combo] [random/combo]\"");
                return;
            }
        }

        logger.info("Game starts: ");
        System.out.println("Welcome to Piraten Karpen Simulator!");

        //Types of rolls are [MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL] in that order starting from enum 0

        logger.info("Begin to play 42 games");
        double[] winPercent = PlayStyle.play(42, randomPlayers);

        if (randomPlayers == 2) {
            System.out.println("Player 1 Strategy: Random\nPlayer 2 Strategy: Random");
        }
        else if (randomPlayers == 1) {
            System.out.println("Player 1 Strategy: Random\nPlayer 2 Strategy: Combo");
        }
        else {
            System.out.println("Player 1 Strategy: Combo\nPlayer 2 Strategy: Combo");
        }
        
        System.out.printf("Player 1 wins: %.2f\nPlayer 2 wins: %.2f\n", winPercent[0] * 100, winPercent[1] * 100);

        logger.info("Completed game");
    }
}
