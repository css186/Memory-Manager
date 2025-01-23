/**
 * A implementation of memory manager interface
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */

public class MemManager {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private int initSize;
    private int poolSize;
    private byte[] memoryPool;
    private FreeBlockList freeList;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param size
     *        buffer pool size
     */
    public MemManager(int size) {
        initSize = size;
        poolSize = size;
        memoryPool = new byte[poolSize];
        freeList = new FreeBlockList(poolSize);
    }
    
    
    // ~ Default Method .........................................
    //
    // ----------------------------------------------------------
    /**
     * Helper function for increasing the size of memory pool
     */
    void increatePoolSize() {
        int oldSize = poolSize;
        poolSize += initSize;
        byte[] newPool = new byte[poolSize];
        System.arraycopy(
            memoryPool, 
            0, 
            newPool, 
            0, 
            memoryPool.length);
        
        // Update memory pool
        memoryPool = newPool;
        
        // Add new space to the free block list
        Handle newHandle = new Handle(oldSize, poolSize - oldSize);
        
        // Insert new handle into free block list
        freeList.insertFreeBlock(newHandle);
        
        // Console output
        System.out.println(
            "Memory pool expanded to " + poolSize + " bytes");
    }
    
    // ~ Public Method .........................................
    //
    // ----------------------------------------------------------
    /**
     * Insert a record and return its position handle.
     * space contains the record to be inserted, of length size.
     * @param space
     *        the byte array of the record to be inserted
     * @param size
     *        the size of the byte array
     * @return
     *        return the suitable space's handle object
     */
    public Handle insert(byte[] space, int size) {
        
        Handle handle = freeList.findAndRemove(size);
        
        while (handle == null) {
            increatePoolSize();
            handle = freeList.findAndRemove(size);
        }
        
        // Check space size
        // Case 1: if the free block is exactly we need
        if (handle.getSize() == size) {
            System.arraycopy(space, 0, memoryPool, 
                handle.getStartPos(), size);
            
            return handle;
        }

        // Case 2: if the free block is larger than needed
        if (handle.getSize() > size) {
            // Split the block
            int remainSize = handle.getSize() - size;
            int remainPos = handle.getStartPos() + size;
            
            
            // Create new handle to insert to free list
            Handle remainHandle = new Handle(remainPos, remainSize);
            freeList.insertFreeBlock(remainHandle);
            
            // Update the returning handle's size
            handle.setSize(size);
            
            // Copy data into memory pool
            System.arraycopy(space, 0, memoryPool, 
                handle.getStartPos(), size);
            
            return handle;

        }
        return null;
        
    }
    
    // ----------------------------------------------------------
    /**
     * Return the length of the record associate with the handle
     * @param h
     *        the handle object
     * @return
     *        the length of handle object
     */
    public int length(Handle h) {
        return h.getSize();
    }
    
    // ----------------------------------------------------------
    /**
     * Free a block at the position specified by the handle
     * Merge adjacent free blocks
     * @param h
     *        the handle to remove
     */
    public void remove(Handle h) {
        freeList.insertFreeBlock(h);
    }
    
    // ----------------------------------------------------------
    /**
     * Return the record with handle position up to size bytes
     * by copying it into space
     * Return the number of bytes actually copied
     * 
     * @param space
     *        the byte array to store
     * @param h
     *        the handle representing the block to read
     * @param size
     *        the size to read
     * @return
     *        The number of bytes actually copied
     *        
     */
    public int get(byte[] space, Handle h, int size) {
        System.arraycopy(memoryPool, h.getStartPos(), space, 0, size);
        return size;
    }

    // ----------------------------------------------------------
    /**
     * Print free block list in string
     * @return
     *        free block list in string
     */
    public String dump() {
        return freeList.dump();
    }


    /**
     * @return the poolSize
     */
    public int getPoolSize() {
        return poolSize;
    }


    /**
     * @param poolSize the poolSize to set
     */
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }


    /**
     * @return the memoryPool
     */
    public byte[] getMemoryPool() {
        return memoryPool;
    }


    /**
     * @param memoryPool the memoryPool to set
     */
    public void setMemoryPool(byte[] memoryPool) {
        this.memoryPool = memoryPool;
    }


    /**
     * @return the freeList
     */
    public FreeBlockList getFreeList() {
        return freeList;
    }


    /**
     * @param freeList the freeList to set
     */
    public void setFreeList(FreeBlockList freeList) {
        this.freeList = freeList;
    }


    /**
     * @return 
     *         the initSize
     */
    public int getInitSize() {
        return initSize;
    }


    /**
     * @param initSize 
     *        the initSize to set
     */
    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }
    
}
