import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import student.TestCase;
/**
 * Test cases for Memory Manager Implementation class
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class MemManagerTest extends TestCase {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private MemManager manager;
    
    // ~ Set up .................................................
    //
    /**
     * Sets up the tests that follow. In general, 
     * used for initialization
     * 
     */
    public void setUp() {
        manager = new MemManager(128);
    }
    
    
    // ~ Public Method............................................
    //
    // ----------------------------------------------------------
    /**
     * Test initialization
     */
    public void testInit() {
        assertEquals(128, manager.getPoolSize());
        assertNotNull(manager.getMemoryPool());
        assertNotNull(manager.getFreeList());
    }
    
    // ----------------------------------------------------------
    /**
     * Test insert
     */
    public void testInsert() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        byte[] data = sem1.serialize();
        Handle h = manager.insert(data, data.length);
        assertNotNull(h);
        assertEquals(h.getStartPos(), 0);
        assertEquals(h.getSize(), 95);
    }
    
    // ----------------------------------------------------------
    /**
     * Test multiple insert
     */
    public void testMultipleInsert() throws Exception {
        byte[] record1 = "First".getBytes();
        byte[] record2 = "Second".getBytes();
        
        Handle handle1 = manager.insert(record1, record1.length);
        Handle handle2 = manager.insert(record2, record2.length);
        
        assertNotNull(handle1);
        assertNotNull(handle2);
        
        byte[] retrieved1 = new byte[record1.length];
        byte[] retrieved2 = new byte[record2.length];
        
        manager.get(retrieved1, handle1, record1.length);
        manager.get(retrieved2, handle2, record2.length);
        
        assertArrayEquals(record1, retrieved1);
        assertArrayEquals(record2, retrieved2);
    }
    
    
    // ----------------------------------------------------------
    /**
     * Test get record back
     * @throws Exception
     *         from byte stream
     */ 
    public void testGetRecord() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        byte[] data = sem1.serialize();
        assertTrue(data.length == 95);
        Handle h = manager.insert(data, 95);
        byte[] retrieved = new byte[95];
        manager.get(retrieved, h, retrieved.length);
        Seminar sem = Seminar.deserialize(retrieved);
        assertEquals(sem.toString(), sem1.toString());
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test increase pool size
     * @throws Exception
     *         from byte stream
     */
    public void testIncreaseSize() throws Exception {
        MemManager m = new MemManager(64);
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        byte[] data = sem1.serialize();
        Handle h = m.insert(data, data.length);
        assertNotNull(h);
        assertEquals(m.getMemoryPool().length, 128);
    }
    
    // ----------------------------------------------------------
    /**
     * Test splitting blocks
     * @throws Exception
     *         from byte stream
     */
    public void testInsertWithSplit() throws Exception {
        MemManager m = new MemManager(256);
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        Seminar sem2 = new Seminar(
            2, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        byte[] data1 = sem1.serialize();
        byte[] data2 = sem2.serialize();
        
        Handle h1 = m.insert(data1, data1.length);
        Handle h2 = m.insert(data2, data2.length);

        assertNotNull(h1);
        assertNotNull(h2);
        assertNotEquals(h1.getStartPos(), h2.getStartPos());
    }
    
    // ----------------------------------------------------------
    /**
     * Test remove functionality
     * @throws Exception
     *         from byte stream
     */
    public void testRemove() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        byte[] data = sem1.serialize();
        Handle h = manager.insert(data, data.length);
        manager.remove(h);
        
        FreeBlockList list = manager.getFreeList();
        ListNode node = list.getHead().getNext();
        Handle newH = node.getHandle();
        assertNotNull(newH);
        assertTrue(newH.getSize() == 128);
        
        byte[] newData = "hello".getBytes();
        Handle wordH = manager.insert(newData, newData.length);
        assertEquals(wordH.getStartPos(), h.getStartPos());
    }
    
    // ----------------------------------------------------------
    /**
     * Test length
     */
    public void testLength() {
        byte[] data = "hello".getBytes();
        Handle h = manager.insert(data, data.length);
        int length = manager.length(h);
        assertNotNull(length);
        assertEquals(data.length, length);
    }
    
    // ----------------------------------------------------------
    /**
     * test dump
     */
    public void testDump() {
        byte[] data = "hello".getBytes();
        manager.insert(data, data.length);
        String output = manager.dump();
        assertNotNull(output);
        assertEquals(output, "(5,123)");

    }
    
    // ----------------------------------------------------------
    /**
     * Test double size 
     */
    public void testDoubleSizeUpdate() {
        MemManager m = new MemManager(16);
        assertEquals(m.getMemoryPool().length, 16);
        
        m.increatePoolSize();
        assertEquals(m.getMemoryPool().length, 32);
        assertEquals(m.getPoolSize(), 32);
        
        m.increatePoolSize();
        assertEquals(m.getMemoryPool().length, 48);
        assertEquals(m.getPoolSize(), 48);
    }
    
    // ----------------------------------------------------------
    /**
     * Test splitting
     */
    public void testSplitting() {
        byte[] record1 = new byte[20];
        byte[] record2 = new byte[30];
        byte[] record3 = new byte[25];
        
        Handle handle1 = manager.insert(record1, record1.length);
        Handle handle2 = manager.insert(record2, record2.length);
        Handle handle3 = manager.insert(record3, record3.length);
        
        manager.remove(handle2); // Create a gap
        
        byte[] smallRecord = new byte[15];
        Handle handle4 = manager.insert(smallRecord, smallRecord.length);
        
        // Should reuse the space from handle2
        assertTrue(handle4.getStartPos() >= 
            handle1.getStartPos() + handle1.getSize());
        assertTrue(handle4.getStartPos() < handle3.getStartPos());
    }
    
    // ----------------------------------------------------------
    /**
     * Test mutation on increasing sizing
     */
    public void testIncreasePoolMutation() {
        byte[] oldPool = manager.getMemoryPool().clone();
        int oldSize = manager.getPoolSize();
        
        // Force doubleSize
        byte[] data = new byte[oldSize + 1];
        manager.insert(data, data.length);
        assertEquals(oldSize * 2, manager.getPoolSize());
    }
    
    // ----------------------------------------------------------
    /**
     * Test free block mutation
     */
    public void testIncreasePoolFreeBlockMutation() {
        // Test for mutations in free block handling
        int originalSize = manager.getPoolSize();
        
        // Create fragmentation
        Handle handle1 = manager.insert(new byte[20], 20);
        Handle handle2 = manager.insert(new byte[20], 20);
        manager.remove(handle1); // Create a free block
        
        // Force doubleSize
        byte[] largeData = new byte[originalSize + 1];
        manager.insert(largeData, largeData.length);
        
        // Try to insert data smaller than the freed block
        byte[] smallData = new byte[15];
        Handle newHandle = manager.insert(smallData, smallData.length);
        assertTrue("Should reuse freed block instead of new space",
            newHandle.getStartPos() < originalSize);
    }
}
