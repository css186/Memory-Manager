import student.TestCase;

//-------------------------------------------------------------------------
/**
*  Test the Handle class
*
* @author Guann-Luen Chen
* @version 2024.11.12
*/
public class HandleTest extends TestCase {
    
    private Handle handle;
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Set up test Handle object
        handle = new Handle(10, 20);
    }
    
    // ~ Public method ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Test get length
     */
    public void testGetLength() {
        assertNotNull(handle.getSize());
        assertEquals(handle.getSize(), 20);
    }
    
    // ----------------------------------------------------------
    /**
     * Test get starting position
     */
    public void testGetStartPos() {
        assertNotNull(handle.getStartPos());
        assertEquals(handle.getStartPos(), 10);      
    }
    
    // ----------------------------------------------------------
    /**
     * Test set length
     */
    public void testSetLength() {
        handle.setSize(5);
        assertNotNull(handle.getSize());
        assertEquals(handle.getSize(), 5);
    }
    
    // ----------------------------------------------------------
    /**
     * Test set starting position
     */
    public void testSetStartPos() {
        handle.setStartPos(10);
        assertNotNull(handle.getStartPos());
        assertEquals(handle.getStartPos(), 10);
    }
    
}

