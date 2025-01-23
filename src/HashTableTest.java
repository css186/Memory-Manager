import static org.junit.Assert.assertNotEquals;
import student.TestCase;

//-------------------------------------------------------------------------
/**
*  Test the HashTable class
*
* @author Guann-Luen Chen
* @version 2024.11.12
*/
public class HashTableTest extends TestCase {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private HashTable table;
    private int capacity;
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Set up test hashtable object
        capacity = 10;
        table = new HashTable(capacity);
    }
    
    // ~ Public Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * Test hash function and find
     */
    public void testHashFunction() {
        int oldHash = table.hash(5);
        int newHash = Math.abs(5 + 1) % capacity;
        assertNotEquals(oldHash, newHash);
        
        Handle h = new Handle(0, 0);
        table.insert(oldHash, h);
        assertNotNull(table.find(5));
        
    }
    // ----------------------------------------------------------    
    /**
     * Test basic insertion
     */
    public void testInsert() {
        Handle h = new Handle(0, 0);
        assertTrue(table.insert(1,  h));
        assertEquals(1, table.getSize());
        
        Record record = table.find(1);
        assertNotNull(record);
        assertEquals(record.getId(), 1);
        assertEquals(record.getHandle(), h);
    }
    // ----------------------------------------------------------   
    /**
     * Test insert duplicate
     */
    public void testInsertDuplicate() {
        Handle h0 = new Handle(0, 0);
        Handle h1 = new Handle(1, 1);
        
        assertTrue(table.insert(1,  h0));
        assertFalse(table.insert(1,  h1));
        assertEquals(table.getSize(), 1);
    }
    // ----------------------------------------------------------   
    /**
     * Test basic removal
     */
    public void testRemove() {
        Handle h0 = new Handle(0, 0);
        table.insert(1,  h0);
        
        Record record = table.remove(1);
        assertNotNull(record);
        assertEquals(1, record.getId());
        assertEquals(h0, record.getHandle());
        assertEquals(0, table.getSize());
        assertTrue(table.getTable()[table.hash(1)]
            .equals(table.getTombstone()));
        
    }
    // ----------------------------------------------------------   
    /**
     * Test remove non exist record
     */
    public void testRemoveNonExist() {
        assertNull(table.remove(1));
        assertEquals(table.getSize(), 0);
    }
    // ----------------------------------------------------------   
    /**
     * Test basic find
     */
    public void testFind() {
        Handle h0 = new Handle(0, 0);
        table.insert(1,  h0);
        Record record = table.find(1);
        assertNotNull(record);
        assertEquals(1, record.getId());
        assertEquals(h0, record.getHandle());
    }
    // ----------------------------------------------------------    
    /**
     * Test find non exist record
     */
    public void testFindNoneExist() {
        Record record = table.find(1);
        assertNull(record);
    }
    // ----------------------------------------------------------   
    /**
     * Test basic collision
     */
    public void testCollision() {
        Handle h0 = new Handle(0, 0);
        Handle h1 = new Handle(1, 1);
        int key1 = 10;
        int key2 = 20;
        
        assertTrue(table.insert(key1, h0));
        assertTrue(table.insert(key2, h1));
        
        assertEquals(2, table.getSize());
        assertNotNull(table.find(key1));
        assertNotNull(table.find(key2));
        
    }
    // ----------------------------------------------------------   
    /**
     * Test basic resizing
     */
    public void testResizing() {
        int oldCap = table.getCapacity();
        int n = oldCap / HashTable.LOAD_FACTOR;
        for (int i = 0; i < n + 1; i++) {
            table.insert(i, new Handle(i, i));
        }
        int newCap = table.getCapacity();
        assertTrue(newCap == oldCap * 2);
        
        // all elements still accessible
        for (int i = 0; i < n + 1; i++) {
            Record rec = table.find(i);
            assertNotNull(rec);
            assertEquals(i, rec.getId());
        }
        
    }
    // ----------------------------------------------------------    
    /**
     * Test basic probing
     */
    public void testProbing() {
        int index = 5;
        int length = 10;
        assertEquals(5, 
            table.quadraticCalculation(index, 0, length));
        assertEquals(6, 
            table.quadraticCalculation(index, 1, length));
        assertEquals(8, 
            table.quadraticCalculation(index, 2, length));
        assertEquals(1, 
            table.quadraticCalculation(index, 3, length));
    
    }
    // ----------------------------------------------------------
    /**
     * Test edge cases 1
     */
    public void testNegativeKey() {
        Handle h0 = new Handle(0, 0);
        assertTrue(table.insert(-1, h0));
        assertNotNull(table.find(-1));
    }
    // ----------------------------------------------------------    
    /**
     * Test edge cases 2
     */
    public void testZero() {
        Handle h0 = new Handle(0, 0);
        assertTrue(table.insert(0, h0));
        assertNotNull(table.find(0));
    }
    // ----------------------------------------------------------    
    /**
     * Test multiplier
     */
    public void testMultiplier() {
        int n = table.getCapacity();
        for (int i = 0; i < n; i++) {
            table.insert(i, new Handle(i, i));
        }
        assertEquals(HashTable.MULTIPLIER * n, 
            table.getCapacity());
        
        assertNotEquals(3 * n, 
            table.getCapacity());
    }
    // ----------------------------------------------------------   
    /**
     * Test tomb stone functionality
     */
    public void testTombstoneInsertion() {
        Handle h = new Handle(1, 1);
        table.insert(1, h);
        table.remove(1);
        
        table.findIndexOfRecord(1);
        Record[] arr = table.getTable();
        assertTrue(arr[table.hash(1)]
            .equals(table.getTombstone()));
        
        assertTrue(table.insert(1, h));
    }
    // ----------------------------------------------------------    
    /**
     * Mutation test for probing function
     */
    public void testProbingMutation() {
        int key1 = 10;
        int key2 = 20;
        table.insert(key1, new Handle(1, 1));
        table.insert(key2, new Handle(2, 2));
        
        assertNotNull(table.find(key1));
        assertNotNull(table.find(key2));
        int index1 = table.findIndexOfRecord(key1);
        int index2 = table.findIndexOfRecord(key2);
        
        assertNotEquals(index1, index2);
    }
    // ----------------------------------------------------------    
    /**
     * Test load factor
     */
    public void testLoadFactor() {
        int initCap = table.getCapacity();
        int before = initCap / HashTable.LOAD_FACTOR;
        
        for (int i = 0; i < before; i++) {
            assertTrue(table.insert(i,  new Handle(i, i)));
            assertEquals(initCap, table.getCapacity());
        }
        assertTrue(table.insert(before, new Handle(5, 5)));
        assertEquals(initCap * HashTable.MULTIPLIER, 
            table.getCapacity());
    }
    // ----------------------------------------------------------    
    /**
     * Test size
     */
    public void testSize() {
        assertEquals(0, table.getSize()); 
        table.insert(1, new Handle(1, 1));
        assertEquals(1, table.getSize());  
        table.remove(1);
        assertEquals(0, table.getSize());
    }
    
    // ----------------------------------------------------------
    /**
     * Test print hash table
     */
    public void testPrint() {
        Handle h = new Handle(1, 1);
        table.insert(1, h);
        table.remove(1);
        table.insert(2, h);
        String out = table.print();
        assertNotNull(out);
        String expected = "1: TOMBSTONE\n"
            + "2: 2\n"
            + "total records: 1";
        assertEquals(out, expected);
    }
    
    // ----------------------------------------------------------
    /**
     * Test existing record
     */
    public void testExistingRecord() {
        Handle h = new Handle(1, 1);
        table.insert(1, h);
        
        int index = table.findIndexOfRecord(1);
        assertNotNull(index);
        assertNotEquals(index, -1);
        assertEquals(1, table.getTable()[index].getId());
    }
    
    // ----------------------------------------------------------
    /**
     * Test non existing record
     */
    public void testNonExistingRecord() {
        Handle h = new Handle(1, 1);
        table.insert(1, h);
        
        int index = table.findIndexOfRecord(2);
        assertNotNull(index);
        assertEquals(index, -1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test finding tomb stone
     */
    public void testFindTombstone() {
        Handle h = new Handle(1, 1);
        table.insert(1, h);
        
        int index = table.findIndexOfRecord(1);
        table.getTable()[index] = table.getTombstone();
        int newIndex = table.findIndexOfRecord(1);
        assertNotNull(newIndex);
        assertEquals(newIndex, -1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test finding with Hash collision
     */
    public void testFindHashCollision() {
        Handle h1 = new Handle(1, 1);
        Handle h2 = new Handle(2, 2);
        table.insert(5, h1);
        table.insert(15, h2);
        int index1 = table.findIndexOfRecord(5);
        int index2 = table.findIndexOfRecord(15);
        assertNotEquals(index1, -1);
        assertNotEquals(index2, -1);
        assertNotEquals(index1, index2);
    }
    
    // ----------------------------------------------------------
    /**
     * Test finding with full table
     */
    public void testFindWithFullTable() {
        for (int i = 0; i < 10; i++) {
            table.insert(i, new Handle(i, i));
        }
        int index = table.findIndexOfRecord(20);
        assertNotNull(index);
        assertEquals(index, -1);
    }
}