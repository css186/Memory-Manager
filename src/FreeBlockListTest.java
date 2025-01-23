import student.TestCase;
/**
 * Test cases for Free Block List
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class FreeBlockListTest extends TestCase {
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
}
