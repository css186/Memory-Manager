/**
 * A implementation of controller class that determine
 * the operations to react with hash table and memory manager
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */
public class Controller {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private HashTable table;
    private MemManager manager;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param memoSize
     *        size for memory pool
     * @param hashSize
     *        size for hash table
     */
    public Controller(int memoSize, int hashSize) {
        table = new HashTable(hashSize);
        manager = new MemManager(memoSize);
    }
    
    // ~ Public Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * insert method
     * @param id
     *        id as key
     * @param seminar
     *        seminar object
     * @return
     *         true if insert successfully
     * @throws Exception
     *         from byte stream
     */
    public boolean insert(int id, Seminar seminar) throws Exception {
        // Get seminar id
        int key = id;
                
        
        // Search key in hash table
        if (table.find(key) != null) {
            System.out.println(
                "Insert FAILED - There is already a record with ID " + 
                key);
            return false;
        }
        
        // Serialize the seminar object
        byte[] serializedSem = seminar.serialize();
        
        // Insert into memory manager
        Handle handle = manager.insert(
            serializedSem, 
            serializedSem.length);
        
        int oldCap = table.getCapacity();
        table.insert(key, handle);
        int newCap = table.getCapacity();
        if (newCap > oldCap) {
            System.out.println("Hash table expanded to " + newCap + " records");
        }

        System.out.println("Successfully inserted record with ID " + key);
        String output = seminar.toString();
        System.out.println(output);
        System.out.println("Size: " + serializedSem.length);

        
        return true;
        
    }
    
    // ----------------------------------------------------------
    /**
     * Search method
     * @param id
     *        id as key
     * @return
     *        true if found
     * @throws Exception
     *         from byte stream
     */
    public boolean search(int id) throws Exception {
        int key = id;
        Record record = table.find(key);
        if (record == null) {
            System.out.println(
                "Search FAILED -- There is no record with ID " +
                key);
            return false;
        }
        
        System.out.println("Found record with ID " + key + ":");
        // Retrieve handle from record
        Handle handle = record.getHandle();
        byte[] data = new byte[handle.getSize()];
        manager.get(data, handle, handle.getSize());
        
        Seminar seminar = Seminar.deserialize(data);
        
        String output = seminar.toString();
        System.out.println(output);     
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * Delete method
     * @param id
     *        id as key
     * @return
     *        true if delete successfully
     */
    public boolean delete(int id) {
        int key = id;
        Record record = table.find(key);
        if (record == null) {
            System.out.println(
                "Delete FAILED -- There is no record with ID " +
                key);
            return false;
        }
        
        Handle handle = record.getHandle();
        
        // Remove id from hash table
        table.remove(key);
        
        // Remove the memory
        manager.remove(handle);
        
        System.out.println(
            "Record with ID " + id + 
            " successfully deleted from the database");
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * print hash table
     */
    public void printHashTable() {
        System.out.println("Hashtable:");
        String output = table.print();
        System.out.println(output);
    }
    
    // ----------------------------------------------------------
    /**
     * print free list
     */
    public void printList() {
        System.out.println("Freeblock List:");
        String output = manager.dump();
        if (!output.isEmpty()) {
            System.out.println(output);
        }
        else {
            System.out.println(
                "There are no freeblocks in the memory pool");
        }
    }

    /**
     * @return 
     *         the table
     */
    public HashTable getTable() {
        return table;
    }

    /**
     * @param table 
     *        the table to set
     */
    public void setTable(HashTable table) {
        this.table = table;
    }

    /**
     * @return 
     *         the manager
     */
    public MemManager getManager() {
        return manager;
    }

    /**
     * @param manager 
     *        the manager to set
     */
    public void setManager(MemManager manager) {
        this.manager = manager;
    }
    
    
}
