
package javaproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hungarian game is a game played by dominos. It consists of 2 to 4 players.
 * Shuffled dominos are handed out to players. The player with the biggest
 * double domino starts the round. He stops playing when he cant put other 
 * dominos in the domino line. Then the player next to him has the turn to play.
 * When one player is being left without dominos the round stops and he gains
 * as points as every other player has in his hands. This means that he gains
 * the sum of every number the other players have in their dominos. In addition
 * if no one has any domino tile to fit the round stops and the player with the 
 * least points on his hand wins the round and gains every point that the other 
 * players have plus his own points. The game stops when one player reaches 100
 * points.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos
 */
public class HungarianMode extends Game{
    protected int currentPlayer;
    protected HashMap<Integer,ArrayList<Domino>> dominosOfPlayer;
    protected HashMap<Integer,Integer> pointsOfPlayer;

    /**
     * Creates a new hungarian game.
     * @param players the ammount of players to play the game.
     */
    public HungarianMode(int players){
        this.players=new int[players];
        pointsOfPlayer=new HashMap<>();
        for(int i=1;i<=players;i++){
            pointsOfPlayer.put(i, 0);
        }
    }

    /**
     * Creates a new round for the hungarian game.
     */
    public void newRound(){
        dominoSet=new DominoSet(biggestNumberOfDominos);
        dominoLine=new ArrayList<>();
        dominosOfPlayer=new HashMap<>();
        shuffleDominos();
        giveEachPlayerDominos();
        setFirstPlayer();
    }

    /**
     * Shuffles the domino set.
     */
    public void shuffleDominos(){
        dominoSet.shuffle();
    }

    /**
     * Hands dominos out on players.
     */
    public void giveEachPlayerDominos(){
        int j,u=0;
        ArrayList<Domino> dominos;
        int number=24/players.length;
        for(int i=1;i<=players.length;i++){
            dominos=new ArrayList<>();
            for(j=u;j<number;j++){
                dominos.add(dominoSet.getDomino(j));
            }
            u=j;
            number+=24/players.length;
            dominosOfPlayer.put(i,dominos);
        }
    }
    
    /**
     * Finds the player with the biggest double domino and sets him first player
     */
    public void setFirstPlayer(){
        ArrayList<Domino> dominos;
        int biggestDoubleNumber=0;
        int player=0;
        for(int i=1;i<=players.length;i++){
            dominos=dominosOfPlayer.get(i);
            for(int j=0;j<dominos.size();j++){
                Domino domino=dominos.get(j);
                if(domino.getLeft()==domino.getRight()){
                    if(biggestDoubleNumber<domino.getLeft()){
                        biggestDoubleNumber=domino.getLeft();
                        player=i;
                    }
                }
            }
            currentPlayer=player;
        }
    }

    /**
     * Passes the turn on the next player.
     */
    public void nextPlayer(){
        currentPlayer++;
        if(currentPlayer>players.length){
            currentPlayer=1;
        }
    }
    
    /**
     * Shows whether a player has dominos to play.
     * @return true if the player can continue to play else it returns false
     */
    public boolean playerCanPlay(){
        ArrayList<Domino> dominos=dominosOfPlayer.get(currentPlayer);
        if(dominos.isEmpty()){
            return false;
        }
        for(int i=0;i<dominos.size();i++){
            if(fitsOnDominoLine(dominos.get(i))){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Shows if someone has dominos to play.
     * @return true if any of the players can play else it returns false
     */
    public boolean solutionExists(){
        if(dominosOfPlayer.get(currentPlayer).isEmpty()){
            return false;
        }
        for(int i=0;i<players.length;i++){
            if(playerCanPlay()){
                return true;
            }
            nextPlayer();
        }
        return false;
    }
    
    /**
     * Finds the player who won the round and computes his points.
     * @return an integer which shows the player who won the game.
     */
    public int playerWhoWonTheRound(){
        int leastPoints=computePoints(dominosOfPlayer.get(currentPlayer));
        int playerWhoWon=currentPlayer;
        int allPoints=leastPoints;
        for(int i=0;i<players.length-1;i++){
            nextPlayer();
            int points=computePoints(dominosOfPlayer.get(currentPlayer));
            allPoints+=points;
            if(points<leastPoints){
                leastPoints=points;
                playerWhoWon=currentPlayer;
            }
        }
        allPoints+=pointsOfPlayer.get(playerWhoWon);
        pointsOfPlayer.put(playerWhoWon,allPoints);
        return playerWhoWon;
    }
    
    /**
     * Computes the points of the dominos that a player has.
     * @param dominos a set of dominos
     * @return the points of this set of dominos
     */
    private int computePoints(ArrayList<Domino> dominos){
        int points=0;
        for(int i=0;i<dominos.size();i++){
            Domino domino=dominos.get(i);
            points+=domino.getLeft()+domino.getRight();
        }
        return points;
    }
    
    /**
     * Returns the points that a player has.
     * @param player a player
     * @return the ammount of points a player has
     */
    public int pointsOfPlayer(int player){
        return pointsOfPlayer.get(player);
    }

    /**
     * Returns the player who has the turn to play.
     * @return an integer which shows the current player
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Adds a domino in the domino line. It is mainly used for bots
     * @param domino the domino we want to add on the domino line
     */
    @Override
    public void addDomino(Domino domino){
        dominosOfPlayer.get(currentPlayer).remove(domino);
        super.addDomino(domino);
    }
    
     /**
     * Tries to add a domino in the domino line on the specific location. On the
     * beggining if the location equals to left otherwise on the end.
     * @param domino the domino we want to add
     * @param location the location in which the domino will be put
     * @return if the domino has been added
     */
    @Override
    public boolean addDomino(Domino domino,String location){
        if(super.addDomino(domino,location)){
            dominosOfPlayer.get(currentPlayer).remove(domino);
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * This is an automatic player. He plays until there is no other domino to
     * fit in the domino line. He chooses the first domino that can fit on the
     * domino line and he puts it in.
     */
    public void bot(){
        ArrayList<Domino> dominos=dominosOfPlayer.get(currentPlayer);
        do{
            for(int i=0;i<dominos.size();i++){
                Domino domino=dominos.get(i);
                if(fitsOnDominoLine(domino)){
                    addDomino(domino);
                }
            }
        }while(playerCanPlay());
    }

    /**
     * Returns the dominos of the player that has the turn.
     * @return a set of dominos that a player has.
     */
    public ArrayList<Domino> getDominosOfCurrentPlayer(){
        return dominosOfPlayer.get(currentPlayer);
    }
    public ArrayList<Domino> getDominosOfPlayer(int player){
        return dominosOfPlayer.get(player);
    }
    public boolean gameIsOver(){
        for(int i=1;i<=players.length;i++){
            if(pointsOfPlayer.get(i)>=100){
                currentPlayer=i;
                return true;
            }
        }
        return false;
    }
    public int getPlayers(){
        return players.length;
    }
}
