/**
 * A implementation of free block list similar to
 * a doubly linked list
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.13
 */
public class FreeBlockList {
    // ~ Fields .................................................
    //
    // ----------------------------------------------------------
    private ListNode head;
    private ListNode tail;
    
    // ~ Constructors ...........................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param length
     *        size for handle object
     */
    public FreeBlockList(int length) {
        head = new ListNode();
        tail = new ListNode();
        ListNode first = new ListNode(new Handle(0, length));
        
        head.setNext(first);
        first.setNext(tail);
        
        tail.setPrev(first);
        first.setPrev(head);
            
    }
    
    // ~ Default Method .........................................
    //
    // ----------------------------------------------------------
    /**
     * Remove node
     * @param node
     *        node to remove
     */
    void remove(ListNode node) {
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
    }
    
    // ----------------------------------------------------------
    /**
     * Merge spaces
     * @param node
     *        node to merge
     */
    void merge(ListNode node) {
        // Get current handle
        Handle currHandle = node.getHandle();
        
//        System.out.println("Attempting to merge block at pos=" + 
//            currHandle.getStartPos() + ", 
//        size=" + currHandle.getSize());
        
        
        // Merge with previous block if adjacent
        if (node.getPrev() != head) {
            Handle prevHandle = node.getPrev().getHandle();
            if (prevHandle.getStartPos() + 
                prevHandle.getSize() == currHandle.getStartPos()) {
                
                // Merge current block into previous block
                prevHandle.setSize(
                    prevHandle.getSize() + 
                    currHandle.getSize());
                
                remove(node);
                // Update node and currHandle 
                // to point to the previous node
                node = node.getPrev();
                currHandle = node.getHandle();
                
//                System.out.println(
//                "Merged with previous - New block: pos=" + 
//                    prevHandle.getStartPos() + ", 
//                size=" + prevHandle.getSize());
            }
        }
        
        // Merge with next block if adjacent
        if (node.getNext() != tail) {
            Handle nextHandle = node.getNext().getHandle();
            if (currHandle.getStartPos() + 
                currHandle.getSize() == nextHandle.getStartPos()) {
                
                // Merge sizes into current block
                currHandle.setSize(
                    currHandle.getSize() + 
                    nextHandle.getSize());
                
                remove(node.getNext());
//                System.out.println(
//                "Merged with next - New block: pos=" + 
//                    currHandle.getStartPos() + ", 
//                size=" + currHandle.getSize());
            }
        }
    }
    
    // ~ Public Method ..........................................
    //
    // ----------------------------------------------------------
    /**
     * Find the first suitable block and remove it
     * @param size
     *        require size for empty block
     * @return
     *        handle object with position and size
     */
    public Handle findAndRemove(int size) {
        ListNode curr = head.getNext();
        
        while (curr != tail) {
            Handle handle = curr.getHandle();
            
            if (handle.getSize() >= size) {
                remove(curr); 
                return handle;
            }
            curr = curr.getNext();
        }
        // Return null if no suitable block found
        return null;
    }
    
    // ----------------------------------------------------------
    /**
     * Insert free block to the list and merge if adjacent
     * @param handle
     *        handle that represent free block
     */
    public void insertFreeBlock(Handle handle) {
        ListNode newNode = new ListNode(handle);
        ListNode curr = head.getNext();
        
        while (curr != tail && 
            curr.getHandle().getStartPos() < handle.getStartPos()) {
            curr = curr.getNext();
        }
     
        // Insert before curr
        newNode.setNext(curr);
        newNode.setPrev(curr.getPrev());
        curr.getPrev().setNext(newNode);
        curr.setPrev(newNode);
        
//        System.out.println(
//        "Inserted free block: pos=" + handle.getStartPos() + 
//            ", size=" + handle.getSize());
        
        // Merge blocks if applicable
        merge(newNode);
    }

    
    // ----------------------------------------------------------
    /**
     * Print free block list
     * @return
     *        free block list in string
     */
    public String dump() {
        String output = "";
        ListNode curr = head.getNext();
        while (curr != null && curr != tail) {
            Handle h = curr.getHandle();
            output += ("(" + 
                       h.getStartPos() + 
                       "," + 
                       h.getSize() + 
                       ")");
            curr = curr.getNext();
            if (curr != null && curr != tail) {
                output += " -> ";
            }
        }
        return output;
    }

    /**
     * @return the head
     */
    public ListNode getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(ListNode head) {
        this.head = head;
    }

    /**
     * @return the tail
     */
    public ListNode getTail() {
        return tail;
    }

    /**
     * @param tail the tail to set
     */
    public void setTail(ListNode tail) {
        this.tail = tail;
    }
    
}
