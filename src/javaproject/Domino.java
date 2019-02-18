
package javaproject;

/**
 * Domino class refers to a domino tile which is consistuted by 2 integer
 * numbers. If we see it horizontically there is one part on the left side
 * and the other part on the right side.
 * @author Ioannis Maliouris
 * @author Konstantinos Nikopoulos
 */
public class Domino {
    private int left;
    private int right;

    /**
     * Constructs a Domino object which requires 2 numbers. 
     * @param left is the number from the left side of the domino
     * @param right is the number from the right side of the domino
     */
    public Domino(int left,int right){
        this.left=left;
        this.right=right;
    }

    /**
     * Constructs a new domino which copies the parts from another domino.
     * @param domino the domino to be copied
     */
    public Domino(Domino domino){
        left=domino.left;
        right=domino.right;
    }

    /**
     * Returns the left part of the domino.
     * @return
     */
    public int getLeft(){
        return left;
    }

    /**
     * @return the right part of the domino
     */
    public int getRight(){
        return right;
    }

    /**
     * Rotates the domino vertically.
     */
    public void rotate(){
        int supp;
        supp=left;
        left=right;
        right=supp;
    }

    /**
     * Checks whether the left side of a domino is similar to the right side
     * of another domino.
     * @param domino the domino which we want to check its left side
     * @return true if the left side of the domino equals to the right side of
     * the other domino else returns false
     */
    public boolean fitsOnLeft(Domino domino){
        return domino.left==right;
    }

    /**
     * Checks whether the left side of a domino is similar to the left side
     * of another domino.
     * @param domino the domino which we want to check its right side
     * @return true if the right side of the domino equals to the left side of
     * the other domino else returns false
     */
    public boolean fitsOnRight(Domino domino){
        return domino.right==left;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj==this){
            return true;
        }
        if(!(obj instanceof Domino)){
            return false;
        }
        Domino domino=(Domino) obj;
        return (domino.left==left && domino.right==right);
    }
}

