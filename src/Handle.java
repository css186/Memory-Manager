// -------------------------------------------------------------------------
/**
 * This is a class that handles storage in the hash table
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */
public class Handle {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    // starting position in the memory manager array
    private int startPos; 
    // size of the byte array
    private int size; 

    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param startPos
     *        position
     * @param size
     *        length
     */
    public Handle(int startPos, int size) {
        this.startPos = startPos;
        this.size = size;
    }
    
    
    // ~ Public Method .........................................
    //
    // ----------------------------------------------------------
    /**
     * Getter for the size
     * @return the size
     */
    public int getSize() {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * Setter for the size
     * @param size 
     *        the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }


    // ----------------------------------------------------------
    /**
     * Getter for the starting position
     * @return the startPos
     */
    public int getStartPos() {
        return startPos;
    }
    
    // ----------------------------------------------------------
    /**
     * Setter for the starting position
     * @param startPos 
     *        the startPos to set
     */
    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
    
}
