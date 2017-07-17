/******************************************************************************
 *  Compilation:  javac-algs4 Dequeue.java
 *  Execution:    No main method. I only verified via unit testing.
 *
 *  Deque data type.
 * 
 ******************************************************************************/

/**
 * The {@code Deque} class is a generic <em>data type</em> that supports adding
 * and removing items from either the front or the back of the data structure as
 * defined at <a href="http://coursera.cs.princeton.edu/algs4/assignments/queues.html">Programming Assignment 2: Deques and Randomized Queues</a>
 * 
 * @author Ashutosh Grewal
 */
public class Deque<Item> implements Iterable<Item> {
    private Item head, tail;
    private int size;
    
    // construct an empty deque
    public Deque() {
        head = tail = null;
        size = 0;
    }
       
    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
   
    // add the item to the front
   public void addFirst(Item item)        
   /* public void addLast(Item item)           // add the item to the end
   public Item removeFirst()                // remove and return the item from the front
   public Item removeLast()                 // remove and return the item from the end
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   public static void main(String[] args)   // unit testing (optional)
   */
}