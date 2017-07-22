/******************************************************************************
 *  Compilation:  javac-algs4 Dequeue.java
 *  Execution:    No main method. I only verified via unit testing.
 *
 *  Deque data type.
 * 
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code Deque} class is a generic <em>data type</em> that supports adding
 * and removing items from either the front or the back of the data structure as
 * defined at <a href="http://coursera.cs.princeton.edu/algs4/assignments/queues.html">Programming Assignment 2: Deques and Randomized Queues</a>
 * 
 * @author Ashutosh Grewal
 */
    
public class Deque<Item> implements Iterable<Item> {
    private Node head, tail;
    private int size;
    
    /**
     * The {@code Node} in the {@code dequeu}.
     */
    private class Node {
        public Item item;
        public Node next;
        public Node prev;
    }
    
    /**
     * Construct an empty {@code dequeue} by initializing internal variables.
     */
    public Deque() {
        head = tail = null;
        size = 0;
    }
       
    /**
     * Is the deque empty?
     * 
     * @return {@code true} if the {@code deque} is empty.
     *         {@code false} otehrwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }
    
    /**
     * Return the number of items on the {@code deque}.
     * 
     * @return The number of items on the {@code deque}.
     */
    public int size() {
        return size;
    }
   
    /**
     * Add the item to the front of the {@code deque}.
     * 
     * @param item  The item to add.
     * 
     * @throws IllegalArgumentException if {code item == null}.
     */
    public void addFirst(Item item) {
        Node newNode;
        
        if (item == null) {
            throw new IllegalArgumentException("Null item can't be added");
        }
        newNode = new Node();
        newNode.next = head;
        newNode.prev = null;
        if (head == null) {
            head = tail = newNode;
        } else {
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    /**
     * Add the item to the end of the {@code deque}.
     * 
     * @param item  The item to add.
     * 
     * @throws IllegalArgumentException if {@code item == null}.
     */
    public void addLast(Item item) {
        Node newNode;
        
        if (item == null) {
            throw new IllegalArgumentException("Null item can't be added");
        }
        newNode = new Node();
        newNode.next = null;
        if (head == null) {
            newNode.prev = null;
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    
    /**
     * Remove and return the item from the front of the {@code deque}.
     * 
     * @return The item at the front.
     * 
     * @throws NoSuchElementException If trying to remove an item from an empty
     *                                {@code deque}.
     */
    public Item removeFirst() {
        Item item;
        
        if (size == 0) {
            throw new NoSuchElementException("Can't remove an item from empty deque.");
        }
        item = head.item;
        if (head == tail) {
            head = tail = null;
        } else {
            Node nextNode;
            
            nextNode = head.next;
            
            // Break linkage between head and next node.
            nextNode.prev = null;
            head.next = null;
            
            // Set new head.
            head = nextNode;
        }
        size--;
        
        return item;
    }
    
    /**
     * Remove and return the item from the end of the {@code dequeue}.
     * 
     * @return The item at the end.
     * 
     * @throws NoSuchElementException If trying to remove an item from an empty
     *                                {@code deque}.
     */
    public Item removeLast() {
        Item item;
        
        if (size == 0) {
            throw new NoSuchElementException("Can't remove an item from empty deque.");
        }
        item = tail.item;
        if (head == tail) {
            head = tail = null;
        } else {
            Node prevNode;
            
            prevNode = tail.prev;
            
             // Break linkage between tail and second last node.
            prevNode.next = null;
            tail.prev = null;
            
            // Set new tail.
            tail = prevNode;
        }
        size--;
        
        return item;
    }
    
    /**
     * Return an iterator over items in order from front to end.
     * 
     * @return Iterator for this deque.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    /**
     * The {@DequeIterator} class implements the {@code hasNext()} and {@code next}
     * methods from the {@code Iteraror} interface for the {@Dequeu} class.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node current = head;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            Item item;
            
            if (current == null) {
                throw new NoSuchElementException();
            }
            item = current.item;
            current = current.next;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove in the iterator is not supported");
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
    }
}