import student.TestCase;
/**
 * Test cases for Record class
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class RecordTest extends TestCase {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private Handle handle;
    private Record record;
    
    // ~ Set up .................................................
    //
    /**
     * Sets up the tests that follow. In general, 
     * used for initialization
     * 
     */
    public void setUp() {
        handle = new Handle(0, 512);
        record = new Record(1, handle);
    }
    
    
    // ~ Public Method............................................
    //
    // ----------------------------------------------------------
    /**
     * Test constructor
     */
    public void testConstructor() {
        assertNotNull(record);
        assertNotNull(record.getId());
        assertNotNull(record.getHandle());
    }
    
    // ----------------------------------------------------------
    /**
     * Test get id
     */
    public void testGetId() {
        assertEquals(record.getId(), 1);
    }
    
    // ----------------------------------------------------------
    /**
     * Test get handle
     */
    public void testGetHandle() {
        assertEquals(record.getHandle(), handle);
    }
    
    // ----------------------------------------------------------
    /**
     * Test set id
     */
    public void testSetId() {
        record.setId(2);
        assertTrue(record.getId() > 0);
        assertEquals(record.getId(), 2);
    }
    
    // ----------------------------------------------------------
    /**
     * Test set handle
     */
    public void testSetHandle() {
        Handle h = new Handle(100, 100);
        record.setHandle(h);
        assertNotNull(record.getHandle());
        assertEquals(record.getHandle(), h);
    }
    
    // ----------------------------------------------------------
    /**
     * Test null
     */
    public void testSetHandleToNull() {
        record.setHandle(null);
        assertNull(record.getHandle());
    }
    
    // ----------------------------------------------------------
    /**
     * Test id negative
     */
    public void testSetNegative() {
        record.setId(-100);
        assertTrue(record.getId() < 0);
        assertEquals(record.getId(), -100);
    }
}
