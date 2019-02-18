
package javaproject;

import java.util.ArrayList;

/**
 * Cardinal game is a game played by dominos. It consists of 2 to 4 players.
 * Shuffled dominos are handed out to players. The player with the biggest
 * double domino starts the round. He stops playing when he cant put other 
 * dominos in the domino line. In order to put a domino in the domino line the
 * sum of the 2 sides of the dominos must be 7. There are cardinal dominos that 
 * can be put everywhere. Cardinal dominos are the dominos which the sum of 
 * their sides is 7 or 0.Then the player next to him has the turn to play. A 
 * player can take one domino from the deck when he can still play, otherwise 
 * when he cant he takes dominos from the deck until he can play .When one 
 * player is being left without dominos the round stops and he gains as points 
 * as every other player has in his hands. This means that he gains the sum of 
 * every number the other players have in their dominos. In addition if no one 
 * has any domino tile to fit the round stops and the player with the least
 * points on his hand wins the round and gains every point that the other 
 * players have plus his own points. The game stops when one player reaches 100 
 * points.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos
 */
public class Cardinal extends HungarianMode{
    private ArrayList<Domino> set;
    private boolean[] gotDominos; 
    /**
     * Creates a new Cardinal game.
     * @param players the ammount of players to play the game.
     */
    public Cardinal(int players){
        super(players);
        gotDominos=new boolean[4];
        for(int i=0;i<4;i++){
            gotDominos[i]=false;
        }
    }
    /**
     * Hands dominos out on players.
     */
    @Override
    public void giveEachPlayerDominos(){
        set=new ArrayList<>();
        int j,u=0;
        ArrayList<Domino> dominos;
        int number,dominosOfEachPlayer;
        if(players.length==2){
            dominosOfEachPlayer=7;
        }
        else{
            dominosOfEachPlayer=5;
        }
        number=dominosOfEachPlayer;
        for(int i=1;i<=players.length;i++){
            dominos=new ArrayList<>();
            for(j=u;j<number;j++){
                dominos.add(dominoSet.getDomino(j));
            }
            u=j;
            number+=dominosOfEachPlayer;
            dominosOfPlayer.put(i,dominos);
        }
        number-=dominosOfEachPlayer;
        for(int i=number;i<dominoSet.size();i++){
            set.add(dominoSet.getDomino(i));
        }
    }
    /**
     * Checks whether a domino is cardinal
     * @param domino the domino to be checked
     * @return if the domino is cardinal
     */
    public boolean isCardinal(Domino domino){
        return (domino.getLeft()+domino.getRight()==7 || domino.getLeft()+domino.getRight()==0);
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
        if(dominoLine.isEmpty()){
            dominosOfPlayer.get(currentPlayer).remove(domino);    
            dominoLine.add(domino);
        }
        if(location.equals("left")){
            if(isCardinal(domino)){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                dominoLine.add(0,domino);
                return true;
            }
            Domino dominoFromLine=dominoLine.get(0);
            if(isCardinal(dominoFromLine)){
                if(domino.getRight()==0){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    dominoLine.add(0,domino);
                    return true;
                }
                else if(domino.getLeft()==0){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    domino.rotate();
                    dominoLine.add(0,domino);
                    return true;
                }
                else if(domino.getRight()+dominoFromLine.getRight()==7){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    dominoLine.add(0,domino);
                    return true;
                }
                else if(domino.getLeft()+dominoFromLine.getRight()==7){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    domino.rotate();
                    dominoLine.add(0,domino);
                    return true;
                }
            }
            if(domino.getRight()+dominoFromLine.getLeft()==7){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                dominoLine.add(0,domino);
                return true;
            }
            else if(domino.getLeft()+dominoFromLine.getLeft()==7){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                domino.rotate();
                dominoLine.add(0,domino);
                return true;
            }
        }
        else{
            if(isCardinal(domino)){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                dominoLine.add(domino);
                return true;
            }
            Domino dominoFromLine=dominoLine.get(dominoLine.size()-1);
            if(isCardinal(dominoFromLine)){
                if(domino.getLeft()==0){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    dominoLine.add(domino);
                    return true;
                }
                else if(domino.getRight()==0){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    domino.rotate();
                    dominoLine.add(domino);
                    return true;
                }
                if(domino.getLeft()+dominoFromLine.getLeft()==7){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    dominoLine.add(domino);
                    return true;
                }
                else if(domino.getRight()+dominoFromLine.getLeft()==7){
                    dominosOfPlayer.get(currentPlayer).remove(domino);
                    domino.rotate();
                    dominoLine.add(domino);
                    return true;
                }
            }
            if(domino.getLeft()+dominoFromLine.getRight()==7){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                dominoLine.add(domino);
                return true;
            }
            else if(domino.getRight()+dominoFromLine.getRight()==7){
                dominosOfPlayer.get(currentPlayer).remove(domino);
                domino.rotate();
                dominoLine.add(domino);
                return true;
            }
        }
        return false;
    }
    /**
     * Adds a domino in the domino line. It is mainly used for bots
     * @param domino the domino we want to add on the domino line
     */
    @Override
    public void addDomino(Domino domino){
        dominosOfPlayer.get(currentPlayer).remove(domino);
        if(isCardinal(domino)){
            dominoLine.add(domino);
        }
        else{
            if(!addDomino(domino,"left")){
                addDomino(domino,"right");
            }
        }
    }
    /**
     * It checks whether a domino can be added either on beggining or in the
     * end of the domino line.
     * @param domino the domino we want to check if fits
     * @return true if it can be put on the domino line else it returns false
     */
    @Override
    public boolean fitsOnDominoLine(Domino domino){
        if(dominoLine.isEmpty()){
            return true;
        }
        if(isCardinal(domino)){
            return true;
        }
        Domino dominoFromLine=dominoLine.get(0);
        if(isCardinal(dominoFromLine)){
            if(domino.getLeft()==0){
                return true;
            }
            if(domino.getRight()==0){
                return true;
            }
            if(domino.getRight()+dominoFromLine.getRight()==7){
                return true;
            }
            else if(domino.getLeft()+dominoFromLine.getRight()==7){
                return true;
            }
        }
        if(domino.getRight()+dominoFromLine.getLeft()==7){
            return true;
        }
        else if(domino.getLeft()+dominoFromLine.getLeft()==7){
            return true;
        }
        dominoFromLine=dominoLine.get(dominoLine.size()-1);
        if(isCardinal(dominoFromLine)){
            if(domino.getLeft()==0){
                return true;
            }
            if(domino.getRight()==0){
                return true;
            }
            if(domino.getRight()+dominoFromLine.getLeft()==7){
                return true;
            }
            else if(domino.getLeft()+dominoFromLine.getLeft()==7){
                return true;
            }
        }
        if(domino.getLeft()+dominoFromLine.getRight()==7){;
            return true;
        }
        else if(domino.getRight()+dominoFromLine.getRight()==7){
            return true;
        }
        return false;
    }
    /**
     * Gives a domino to the current player from the deck if there are any left.
     * @return if a domino is taken or not
     */
    public boolean getDomino(){
        if(set.size()>2){
            ArrayList<Domino> dominos=dominosOfPlayer.get(currentPlayer);
            dominos.add(set.get(set.size()-1));
            set.remove(set.size()-1);
            dominosOfPlayer.put(currentPlayer,dominos);
            return true;
        }
        return false;
    }
    /**
     * Gives dominos to the current player until he can play
     * @return if the player got dominos
     */
    public boolean getDominos(){
        for(int i=1;i<=4;i++){
            if(i!=currentPlayer){
                gotDominos[i-1]=false;
            }
        }
        if(gotDominos[currentPlayer-1]==true){
            return false;
        }
        if(set.size()==2){
            return false;
        }
        while(!playerCanPlay()){
            if(set.size()==2){
                return true;
            }
            getDomino();
        }
        gotDominos[currentPlayer-1]=true;
        return true;
    }
    /**
     * This is an automatic player. He plays until there is no other domino to
     * fit in the domino line. He chooses the first domino that can fit on the
     * domino line and he puts it in.
     */
    @Override
    public void bot(){
        super.bot();
        if(getDominos()){
            super.bot();
        }
    }
    /**
     * Shows if someone has dominos to play.
     * @return true if any of the players can play else it returns false
     */
    @Override
    public boolean solutionExists(){
        if(dominosOfPlayer.get(currentPlayer).isEmpty()){
            return false;
        }
        for(int i=0;i<players.length;i++){
            if(playerCanPlay()){
                return true;
            }
            else if(set.size()>2){
                nextPlayer();
                return true;
            }
            nextPlayer();
        }
        return false;
    }
    /**
     * Checks if the current player has any dominos.
     * @return true if player has dominos else false.
     */
    public boolean playerHasDominos(){
        return !dominosOfPlayer.get(currentPlayer).isEmpty();
    }
    /**
     * This method returns the size of the dominoSet.
     * @return the size of the domino deck.
     */
    public int getSetSize(){
        return set.size();
    }
}
