import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import pk.PlayStyle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

import java.util.Objects;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);

    private static int addCmdOptions(String[] args) {
        //This method is responsible for taking care of command line arguments using Apache Commons CLI

        //First create a options object and a help formatter
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        //Create all the necessary options, including how many random players I want to have and what mode I should play the game in
        Option normalMode = new Option("normal", false, "Play the game normally with no logger displays");
        Option traceMode = new Option( "trace", false, "Play the game in trace mode");
        Option RandomPlayers = Option.builder("RandomPlayers")
                .argName("Number")
                .hasArg(true)
                .desc("Enter the amount of random players you want. Maximum players: 2. Assume playing combo as default")
                .build();

        //Create a new mutually exclusive group for the normal mode and trace mode options
        OptionGroup mode = new OptionGroup();
        mode.addOption(normalMode);
        mode.addOption(traceMode);

        //Add these options to the accepted options list
        options.addOptionGroup(mode);
        options.addOption(RandomPlayers);

        //Create a new command line parser
        CommandLineParser parser = new DefaultParser();

        //Create a variable and default the game to having 0 random players
        int numRandomPlayers = 0;

        //This try block is in case there is an error with the inputs or parsing
        try {

            //Create a command line variable
            CommandLine cmd = parser.parse(options, args);

            //If it has the option of RandomPlayers
            if (cmd.hasOption("RandomPlayers")) {

                //Then check what value I get from it
                String num = cmd.getOptionValue("RandomPlayers");

                //I store the number in numRandomPlayers and in case of number format exception, I already have it covered
                numRandomPlayers = Integer.parseInt(num);

                //This is to check they entered a valid input for random players
                if (numRandomPlayers > 2 || numRandomPlayers < 0) {
                    throw new ParseException("Incorrect number of random players");
                }
            }

            //If they entered normal as the play style, then set the logger error level to show Error or higher
            if (cmd.hasOption("normal")) {
                Logger logger = LogManager.getRootLogger();
                Configurator.setAllLevels(logger.getName(), Level.getLevel("ERROR"));
            }

            //If it is not normal or trace, then give an error
            else if (! cmd.hasOption("trace")) {
                throw new ParseException("Please enter what mode the game should be played");
            }

        }

        //Catch any parse exceptions or number format exceptions by converting string to integer
        catch (ParseException | NumberFormatException exp) {

            //Oh no, something went wrong
            logger.fatal("Parsing failed. Reason: " + exp.getMessage());
            formatter.printHelp("mvn -q exec:java -Dexec.mainClass=PiratenKarpen -Dexec.args=\"<Insert arguments here>\"", options);
            System.exit(0);
        }

        //Return the number of random players
        return numRandomPlayers;
    }
    public static void main(String[] args) {
        //Main start to the game

        //Get how many random players there are from the inputs
        int randomPlayers = addCmdOptions(args);

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
