/**
 * A class to store id-Handle pair
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */
public class Record {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private int id;
    
    // the handle returned from the memory manager
    private Handle handle;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param id
     *        id as key
     * @param handle
     *        handle object
     */
    public Record(int id, Handle handle) {
        this.id = id;
        this.handle = handle;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the handle
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * @param handle the handle to set
     */
    public void setHandle(Handle handle) {
        this.handle = handle;
    } 
}
