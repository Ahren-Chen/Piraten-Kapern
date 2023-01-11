import pk.Dice;
import pk.Player;

import java.util.Arrays;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");

        //Here I will roll 8 dice and store them in an array called "rolledDice"
        //Types of rolls are [MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL] in that order starting from enum 0
        Dice myDice = new Dice();
        System.out.println(Arrays.toString(myDice.roll8()));
        System.out.println();
        System.out.println("That's all folks!");
    }

    public static int[] playRandom(int games) {
        Player player1 = new Player();
        Player player2 = new Player();
        return null;
    }
}
