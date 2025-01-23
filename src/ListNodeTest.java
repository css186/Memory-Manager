import student.TestCase;
/**
 * Test cases for Record class
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class ListNodeTest extends TestCase {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private Handle h1;
    private Handle h2;
    private ListNode node1;
    private ListNode node2;
    private ListNode node3;
    
    // ~ Set up .................................................
    //
    /**
     * Sets up the tests that follow. In general, 
     * used for initialization
     * 
     */
    public void setUp() {
        h1 = new Handle(0, 512);
        h2 = new Handle(100, 100);
        
        node1 = new ListNode(h1);
        node2 = new ListNode(h2);
        node3 = new ListNode();
    }
    
    
    // ~ Public Method............................................
    //
    // ----------------------------------------------------------
    /**
     * Test constructor with no parameters
     */
    public void testDefaultConstruct() {
        assertNull(node3.getHandle());
        assertNull(node3.getNext());
        assertNull(node3.getPrev());
    }
    
    // ----------------------------------------------------------
    /**
     * Test constructor with parameters
     */
    public void testParamConstruct() {
        assertEquals(node1.getHandle(), h1);
        assertNull(node1.getNext());
        assertNull(node1.getPrev());
    }
    
    // ----------------------------------------------------------
    /**
     * Test get/set prev
     */
    public void testGetSetPrev() {
        node1.setPrev(node2);
        assertNotNull(node1.getPrev());
        assertEquals(node2, node1.getPrev());
        
        node1.setPrev(null);
        assertNull(node1.getPrev());
    }
    
    // ----------------------------------------------------------
    /**
     * Test get/set next
     */
    public void testGetSetNext() {
        node1.setNext(node2);
        assertNotNull(node1.getNext());
        assertEquals(node2, node1.getNext());
        
        node1.setNext(null);
        assertNull(node1.getNext());
    }
    
    // ----------------------------------------------------------
    /**
     * Test get/set Handle
     */
    public void testGetSetHandle() {
        Handle h3 = new Handle(5, 5);
        node3.setHandle(h3);
        assertNotNull(node3.getHandle());
        assertEquals(h3, node3.getHandle());
    }

    // ----------------------------------------------------------
    /**
     * Test linking functionality
     */
    public void testLinkage() {
        node1.setNext(node2);
        node2.setPrev(node1);
        
        assertNotNull(node1.getNext());
        assertNotNull(node2.getPrev());
        assertEquals(node2, node1.getNext());
        assertEquals(node1, node2.getPrev());
    }
}
