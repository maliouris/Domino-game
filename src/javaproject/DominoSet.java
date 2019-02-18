
package javaproject;

import java.util.ArrayList;
import java.util.Random;

/**
 * DominoSet class implements the idea of a set of dominos that are needed in
 * order to play a domino game. It's size is flexible and it is dictated by its
 * constructor.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos 
 */
public class DominoSet {
    private ArrayList<Domino> dominoSet;

    /**
     * Constructs a domino deck in with the biggest number of the domino parts.
     * After the construction the order of the deck is 
     * ascending which means that the deck goes like that way
     * (0,0),(0,1)...(biggestNumber,biggestNumber).
     * @param biggestNumber the biggest number to be insterted on dominos
     */
    public DominoSet(int biggestNumber){
        dominoSet=new ArrayList<>();
        for(int i=0;i<=biggestNumber;i++){
            for(int j=i;j<=biggestNumber;j++){
                Domino domino=new Domino(i,j);
                dominoSet.add(domino);
            }
        }
    }
    
    /**
     * Returns a domino from a specific location.
     * @param location the location of the domino in the set.
     * @return
     */
    public Domino getDomino(int location){
        return dominoSet.get(location);
    }
    
    /**
     *  Shuffles the deck in a random way.
     */
    public void shuffle(){
        ArrayList<Domino> shuffled=new ArrayList<>();
        Random random=new Random();
        int size=dominoSet.size();
        for(int i=0;i<size;i++){
            int number=random.nextInt(dominoSet.size());
            shuffled.add(getDomino(number));
            dominoSet.remove(number);
        }
        dominoSet=shuffled;
    }

    /**
     * Returns if there are any dominos left in the deck.
     * @return true if the set has dominos else it returns false
     */
    public boolean isEmpty(){
        return dominoSet.isEmpty();
    }

    /**
     * Returns the ammount of dominos that the deck has.
     * @return the number of dominos in the set.
     */
    public int size(){
        return dominoSet.size();
    }
}
