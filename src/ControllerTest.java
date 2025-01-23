import student.TestCase;

/**
 * Test cases for controller
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class ControllerTest extends TestCase {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private Controller controller;
    
    // ~ Set up .................................................
    //
    /**
     * Sets up the tests that follow. In general, used for initialization
     * 
     */
    public void setUp() {
        controller = new Controller(32, 4);

    }
    
    // ~ Public Method ............................................
    //
    // ----------------------------------------------------------
    /**
     * Test basic insert
     * @throws Exception
     *         from byte stream
     */
    public void testInsertSuccess() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        boolean result = controller.insert(1, sem1);
        assertNotNull(result);
        assertTrue(result);
    }
    
    // ----------------------------------------------------------
    /**
     * Test insert duplicate
     * @throws Exception
     *         from byte stream
     */
    public void testInsertDuplicate() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        Seminar sem2 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        controller.insert(1, sem1);
        boolean result = controller.insert(1, sem2);
        assertNotNull(result);
        assertFalse(result);
    }
    
    // ----------------------------------------------------------
    /**
     * Test search success
     * @throws Exception
     *         from byte stream
     */
    public void testSearchSuccess() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        controller.insert(1, sem1);

        boolean found = controller.search(1);
        assertNotNull(found);
        assertTrue(found);
    }
    
    // ----------------------------------------------------------
    /**
     * Test search on non existing key
     * @throws Exception
     *         from byte stream
     */
    public void testSearchNotFound() throws Exception {
        boolean found = controller.search(2);
        assertNotNull(found);
        assertFalse(found);
    }
    
    // ----------------------------------------------------------
    /**
     * Test delete success
     * @throws Exception
     *         from byte stream
     */
    public void testDeleteSuccess() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        controller.insert(1, sem1);

        boolean deleted = controller.delete(1);
        assertNotNull(deleted);
        assertTrue(deleted);
    }
    
    // ----------------------------------------------------------
    /**
     * Test delete non existing key
     * @throws Exception
     *         from byte stream
     */
    public void testDeleteNonExist() throws Exception {
        boolean deleted = controller.delete(2);
        assertNotNull(deleted);
        assertFalse(deleted);
    }
    
    // ----------------------------------------------------------
    /**
     * Test print empty list
     */
    public void testPrintEmtpyList() {
        controller.printList();
        String output = systemOut().getHistory();
        assertNotNull(output);
        assertEquals(output, "Freeblock List:\n(0,32)\n");
        
    }
    
    // ----------------------------------------------------------
    /**
     * Test insert the exact same size with buffer pool
     * @throws Exception
     *         from byte stream
     */
    public void testInsertExactSize() throws Exception {
        String[] keywords = {"Good", "Bad", "Ugly"};
        
        Seminar sem1 = new Seminar(
            1, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, 
            "This is a great seminar");
        
        byte[] data = sem1.serialize();
        Controller con = new Controller(95, 4);
        con.insert(1, sem1);
        String output = con.getManager().dump();
        assertTrue(output.isEmpty());
        con.printList();
        String listOut = systemOut().getHistory();
        assertNotNull(listOut);
        String expected = "Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Seminar Title\n"
            + "Date: 2405231000, Length: 75, X: 15, Y: 33, Cost: 125\n"
            + "Description: This is a great seminar\n"
            + "Keywords: Good, Bad, Ugly\n"
            + "Size: 95\n"
            + "Freeblock List:\n"
            + "There are no freeblocks in the memory pool\n";
        assertEquals(listOut, expected);
        
    }
}
