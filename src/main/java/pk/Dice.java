package pk;
import java.util.Arrays;
import java.util.Random;

public class Dice {

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        //System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        //System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public Faces[] roll8() {
        Faces[] rolledDice = new Faces[8];
        for (int i = 0; i < 8; i++) {
            rolledDice[i] = roll();
            //System.out.print("DEBUG: " + rolledDice[i] + " ");
        }

        return rolledDice;
    }
    
}
