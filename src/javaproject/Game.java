
package javaproject;

import java.util.ArrayList;

/**
 * Game class refers to a game which is played with dominos. It consists of the
 * number of players that they play the game, the biggest number of the domino 
 * part, the line where you put dominos and a set of dominos.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos
 */
public class Game {

    protected int players[];
    protected static int biggestNumberOfDominos=6;
    protected ArrayList<Domino> dominoLine;
    protected DominoSet dominoSet;
    /**
     * Constructs a domino game by creating a new line for dominos to be put
     * and a set of dominos.
     */
    public Game(){
        dominoLine=new ArrayList<>();
        dominoSet=new DominoSet(biggestNumberOfDominos);
    }

    /**
     * Returns the biggest number of dominos which is consisted in the set.
     * @return an integer which represents the biggest number of dominos
     */
    public int getBiggestNumberOfDominos(){
        return biggestNumberOfDominos;
    }

    /**
     * Returns a doming from the set given a location.
     * @param dominos a set of dominos
     * @param location the location from which we want to get a domino
     * @return a domino from the a set
     */
    public Domino getDomino(ArrayList<Domino> dominos,int location){
        if(dominos.isEmpty()){
            return null;
        }
        return dominos.get(location);
    }

    /**
     * Adds a domino in the domino line. First it checks if it fits on the start
     * of the line. If it fits, the method checks if the domino can be added as
     * it is, otherwise it rotates it and then puts it on the line. If it doesn't
     * fit on the start of the line then does exactly the same for the end of
     * the line.
     * @param domino the domino we want to add on the domino line
     */
    protected void addDomino(Domino domino){
        if(fitsOnBeggining(domino)){
            if(!dominoLine.isEmpty()){
                if(!domino.fitsOnLeft(dominoLine.get(0))){
                    domino.rotate();
                }
            }
            dominoLine.add(0,domino);
        }
        else{
            if(!domino.fitsOnRight(dominoLine.get(dominoLine.size()-1))){
                domino.rotate();
            }
            dominoLine.add(domino);
        }
    }

    /**
     * This method puts a domino in the line. It puts it on the beggining if the
     * location that is given equals to 0 or else it puts it on the ending.
     * @param domino the domino we want to add on the domino line
     * @param location the location in which we want to put it.
     */
    public boolean addDomino(Domino domino,String location){
        if(dominoLine.isEmpty()){
                dominoLine.add(domino);
                return true;
        }
        if(location.equals("left")){
            if(!domino.fitsOnLeft(dominoLine.get(0))){
                domino.rotate();
            }
            if(domino.fitsOnLeft(dominoLine.get(0))){
                dominoLine.add(0,domino);
                return true;
            }
            else{
                domino.rotate();
                return false;
            }
        }
        else{
            if(!domino.fitsOnRight(dominoLine.get(dominoLine.size()-1))){
                domino.rotate();
            }
            if(domino.fitsOnRight(dominoLine.get(dominoLine.size()-1))){
                dominoLine.add(domino);
                return true;
            }
            else{
                domino.rotate();
                return false;
            }
        }
    }

    /**
     * Returns whether a domino fits on the start of the line of dominos. First
     * checks if the line has any domino. Then it checks if the domino can be
     * added in the begging, if not it creates a new domino with the same 
     * numbers rotates it and checks again.
     * @param domino the domino we want to check if fits
     * @return true if it fits on beggining else it returns false
     */
    public boolean fitsOnBeggining(Domino domino){
        if(domino==null){
            return false;
        }
        if(dominoLine.isEmpty()){
            return true;
        }
        if(domino.fitsOnLeft(dominoLine.get(0))){
            return true;
        }
        else{
            Domino newDomino=new Domino(domino);
            newDomino.rotate();
            return newDomino.fitsOnLeft(dominoLine.get(0));
        }
    }

    /**
     * Returns whether a domino fits on the finish of the line of dominos. First
     * checks if the line has any domino. Then it checks if the domino can be
     * added in the ending, if not it creates a new domino with the same 
     * numbers rotates it and checks again.
     * @param domino the domino we want to check if fits
     * @return true if it fits on ending else it returns false
     */
    public boolean fitsOnEnding(Domino domino){
        if(domino==null){
            return false;
        }
        if(dominoLine.isEmpty()){
            return false;
        }
        if(domino.fitsOnRight(dominoLine.get(dominoLine.size()-1))){
            return true;
        }
        else{
            Domino newDomino=new Domino(domino);
            newDomino.rotate();
            return newDomino.fitsOnRight(dominoLine.get(dominoLine.size()-1));
        }
    }

    /**
     * It checks whether a domino can be added either on beggining or on the
     * end of the domino line.
     * @param domino the domino we want to check if fits
     * @return true if it can be put on the domino line else it returns false
     */
    public boolean fitsOnDominoLine(Domino domino){
        if(dominoLine.isEmpty()){
            return true;
        }
        else{
            return (fitsOnBeggining(domino) || fitsOnEnding(domino));
        }
    }

    /**
     * Returns the line of dominos that have been put.
     * @return a set of dominos
     */
    public ArrayList<Domino> getDominoLine(){
        return dominoLine;
    }

    /**
     * Returns the set of dominos.
     * @return a set of dominos
     */
    public DominoSet getDominoSet(){
        return dominoSet;
    }
}
