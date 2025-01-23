/**
 * List node class for the free block list
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */

public class ListNode {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private Handle handle;
    private ListNode prev;
    private ListNode next;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * default constructor
     */
    public ListNode() {
        handle = null;
        prev = null;
        next = null;
    }
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param handle
     *        handle object
     */
    public ListNode(Handle handle) {
        this.handle = handle;
        prev = null;
        next = null;
    }
    
    // ~ Public Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * Get previous node
     * @return
     *         previous node
     */
    public ListNode getPrev() {
        return prev;
    }
    
    /**
     * Set previous node
     * @param prev
     *        List node to be set previous
     */
    public void setPrev(ListNode prev) {
        this.prev = prev;
    }
    
    /**
     * Get next node
     * @return
     *         next node
     */
    public ListNode getNext() {
        return this.next;
    }
    
    /**
     * Set next node
     * @param next
     *        List node to set next
     */
    public void setNext(ListNode next) {
        this.next = next;
    }
    
    /**
     * Get handle
     * @return
     *         handle object
     */
    public Handle getHandle() {
        return handle;
    }
    
    /**
     * Set handle
     * @param h
     *        handle to set
     */
    public void setHandle(Handle h) {
        handle = h;
    }
    
}
