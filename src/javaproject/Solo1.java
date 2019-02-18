
package javaproject;

import java.util.ArrayList;



/**
 * Solo1 is a domino game which is playable by one player. In solo1 the set of 
 * dominos after being suffled it is divided in 4 lines. Player can see every
 * domino but he can use only the last of each of the 4 lines. If none of these
 * dominos fit on the domino line then the game is over and the player loses. He
 * wins only when all lines are empty and no domino is left to be played.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos 
 */
public class Solo1 extends Game{
    private ArrayList<ArrayList<Domino>> lines;
    //I use ArrayList instead of Stack because I want to have more general
    //methods in order to implement them in a parent class.
    /**
     * Constructs a set of dominos, creates 4 lines and creates the domino line.
     */
    public Solo1(){
        dominoSet=new DominoSet(biggestNumberOfDominos);
        lines=new ArrayList<>(4);
        dominoLine=new ArrayList<>();
    }
    
    /**
     * Returns the last domino from a specific line.
     * @param line a set of dominos
     * @return a domino from the line
     */
    public Domino getDomino(ArrayList<Domino> line){
        return super.getDomino(line,line.size()-1);
    }
    
    /**
     * Removes the last domino of the line and returns it.
     * @param line a set of dominos
     * @return a domino from the line
     */
    private Domino popDomino(ArrayList<Domino> line){
        return line.remove(line.size()-1);
    }    

    /**
     * Initializes the lines with dominos of the set.
     */
    public void setLines(){
        ArrayList<Domino> line;
        int j=0,u=7;
        for(int i=0;i<4;i++){
            line=new ArrayList<>();
            while(j<u){
                line.add(dominoSet.getDomino(j));
                j++;
            }
            lines.add(line);
            u+=7;
        }
    }
    
    /**
     * Creates 4 lines from the set of dominos after it is shuffled.
     */
    public void setRandomLines(){
        dominoSet.shuffle();
        lines=new ArrayList<>();
        setLines();
    }
    
    /**
     * Returns all the dominos of a line.
     * @param line a set of dominos
     * @return every domino that a line has
     */
    public ArrayList<Domino> getLine(int line){
        if(line<1 || line>4){
            return new ArrayList<>();
        }
        return lines.get(line-1);
    }
    
    /**
     * Returns whether a game can be continued.
     * @return true if the game can be continued or false if the game is over
     */
    public boolean solutionExists(){
        for(int i=1;i<=4;i++){
            if(fitsOnDominoLine(getDomino(getLine(i)))){
                return true;
            }
        }
        return false;
    }
    
    /** 
     * Adds a domino in the domino line after it removes it from the specific
     * line.
     * @param line a set of dominos
     */
    public void addDomino(ArrayList<Domino> line){
        Domino domino=popDomino(line);
        addDomino(domino);
    }
    
    /** 
     * Adds a domino right or left in the domino line relying on the location 
     * after it removes it from the specific line.
     * @param line a set of dominos
     * @param location the location in which the domino will be put, right 
     * or left of the domino line
     */
    public boolean addDomino(ArrayList<Domino> line,String location){
        Domino domino=getDomino(line);
        if(addDomino(domino,location)){
            popDomino(line);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Return whether the player won the game or not.
     * @return true if the player won the game else it returns false.
     */
    public boolean playerWon(){
        int empties=0;
        for(int i=1;i<=4;i++){
            if(getLine(i).isEmpty()){
                empties++;
            }
        }
        return (empties==4);
    }
}
