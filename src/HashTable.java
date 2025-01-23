/**
 * A implementation of a hash table using array
 * 
 * Rewrite from my own hash table in project 1
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */
public class HashTable {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    /**
     * Load factor for resizing in hash table
     */
    public final static int LOAD_FACTOR = 2;
    
    /**
     * In what multiplier will the array grow
     */
    public final static int MULTIPLIER = 2;
    
    private int size; // actual size of array
    private int capacity;
    private Record[] table;
    private Record tombstone = new Record(Integer.MIN_VALUE, null);
    private int loadFactor;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param capacity
     *        initial size for array
     */
    public HashTable(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.table = new Record[capacity];
    }
    
    // ~ Default Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * A default function to deal with resizing the table
     * Resize multiplier is set to 2
     */
    void resizeTable() {
        int newCapacity = this.capacity * MULTIPLIER;
        Record [] newTable = new Record[newCapacity];
        
        for (Record record : this.table) {
            if (record != null && !record.equals(this.tombstone)) {
                int index = hash(record.getId());
                int i = 1;
                int newIndex = index;
                while (newTable[newIndex] != null) {
                    newIndex = this.quadraticCalculation(index, i, newCapacity);
                    i++;
                }
                newTable[newIndex] = record;
            }
        }
        // Update hash table to the new one
        this.table = newTable;
        this.capacity = newCapacity;
    }
    
    // ~ Public Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * Compute the hash function
     * @param key
     *        key to hash
     * @return
     *        hash code
     */
    public int hash(int key) {
        return Math.abs(key) % capacity;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to compute index based on quadratic
     * @param index
     *        Hash code of input key
     * @param step
     *        Step to move to the next slot
     * @param length
     *        Length of the hash table
     * @return
     *        New hash code
     */
    public int quadraticCalculation(int index, int step, int length) {
        int offset = (step * step + step) / 2;
        return (index + offset) % length;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to insert records into the hash table
     * @param key
     *        key to insert
     * @param h
     *        handle object
     * @return
     *        true if insert successfully
     */
    public boolean insert(int key, Handle h) {
        // Check if key already exist in the table
        if (find(key) != null) {
            return false;
        }
        // Check if size exceed current capacity
        if ((this.size + 1) > (this.capacity / LOAD_FACTOR)) {
            this.resizeTable();
        }
        
        // Find index of slot available for insertion
        int index = this.findIndexToInsert(key);
        if (index == -1) {
            return false;
        }
        this.table[index] = new Record(key, h);
        this.size++;
        
        return true;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to remove record from the table
     * @param key
     *        The key of the data needed to be removed
     * @return
     *        Return the removed data
     */         
    public Record remove(int key) {
        // Empty table case
        if (this.size <= 0) {
            return null;
        }
        
        // Get index
        int index = this.findIndexOfRecord(key);
        if (index == -1) {
            return null;
        }
        
        Record removed = this.table[index];
        this.table[index] = this.tombstone;
        this.size--;
        return removed;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find designated record in the hash table
     * @param key
     *        key of the data
     * @return
     *        Record object
     */
    public Record find(int key) {
        // get hash code
        int index = hash(key);
        int i = 1;
        int newIndex = index;
        while (this.table[newIndex] != null) {
            if (key == this.table[newIndex].getId() && 
                !this.table[newIndex].equals(tombstone)) {
                return this.table[newIndex];
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
            if (i == this.capacity) {
                break;
            }
        }
        return null;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find the index of given record by key
     * @param key
     *        The key of the data
     * @return
     *        Return the index of record if found
     *        Return -1 if else
     */
    public int findIndexOfRecord(int key) {
        int index = hash(key);
        int i = 1;
        int newIndex = index;
        while (this.table[newIndex] != null) {
            if (key == this.table[newIndex].getId()
                && !this.tombstone.equals(this.table[newIndex])) {
                return newIndex;
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
            if (i == capacity) {
                break;
            }
        }
        return -1;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to find the index of either empty slot 
     * or tomb stone slot
     * 
     * @param key
     *        The key of the data
     * @return
     *        Return the index of empty slot or tomb stone
     *        else return -1
     */
    public int findIndexToInsert(int key) {
        // Get hash code
        int index = hash(key);
        int i = 1;
        int newIndex = index;
        while (this.table[newIndex] != null) {
            if (this.tombstone.equals(table[newIndex])) {
                break;
            }
            // Prevent infinite loop
            if (i == this.capacity) {
                return -1;
            }
            newIndex = this.quadraticCalculation(index, i, this.capacity);
            i++;
        }
        return newIndex;
    }

    // ----------------------------------------------------------
    /**
     * Print hash table
     * @return
     *        hash table display in string
     */
    public String print() {
        
        String outputString = "";

        for (int i = 0; i < table.length; i++) {            
            if (this.table[i] != null) {
                outputString += i + ": ";
                
                if (this.tombstone.equals(this.table[i])) {
                    outputString += "TOMBSTONE\n";
                }
                else {
                    outputString += table[i].getId() + "\n";
                }
            }
        }

        outputString += "total records: " + this.size;

        return outputString;

    }
    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the table
     */
    public Record[] getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Record[] table) {
        this.table = table;
    }

    /**
     * @return the tombstone
     */
    public Record getTombstone() {
        return tombstone;
    }

    /**
     * @param tombstone the tombstone to set
     */
    public void setTombstone(Record tombstone) {
        this.tombstone = tombstone;
    }

    /**
     * @return the loadFactor
     */
    public int getLoadFactor() {
        return loadFactor;
    }

    /**
     * @param loadFactor the loadFactor to set
     */
    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

 
}
