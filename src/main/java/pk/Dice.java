package pk;
import java.util.Random;

public class Dice {

    public Faces roll() {
        //This method rolls a singular dice with 6 different faces and outcomes
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public Faces[] roll8() {
        //This method rolls 8 dice at the same time and returns the results in an array of length 8 and type Faces

        //Create a new variable to store the results of the dice rolls in an array
        Faces[] rolledDice = new Faces[8];

        //This loops through all the indexes of the array and rolls a die to store the result in the array
        for (int i = 0; i < 8; i++) {
            rolledDice[i] = roll();
        }

        return rolledDice;
    }
}