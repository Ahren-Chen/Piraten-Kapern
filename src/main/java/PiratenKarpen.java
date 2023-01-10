import pk.Dice;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");

        //Here I will roll 8 dice and store them in an array called "rolledDice"
        //Types of rolls are [MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL] in that order starting from enum 0
        Dice myDice = new Dice();
        pk.Faces[] rolledDice = new pk.Faces[8];
        for (int i = 0; i < 8; i++) {
            rolledDice[i] = myDice.roll();
            System.out.print(rolledDice[i] + " ");
        }
        System.out.println();
        System.out.println("That's all folks!");
    }
    
}
