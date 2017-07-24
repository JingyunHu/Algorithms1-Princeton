/******************************************************************************
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *
 *  Deque data type.
 * 
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code RandomizedQueue} class is similar to a stack or queue, except that
 * the item removed is chosen uniformly at random  from items in the data structure.
 * <a href="http://coursera.cs.princeton.edu/algs4/assignments/queues.html">Programming Assignment 2: Deques and Randomized Queues</a>
 * 
 * @author Ashutosh Grewal
 */
    
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int size;
    private static final int INIT_ARR_SIZE = 2;
    
    /**
     * Construct an empty {@code RandomizedQueue} by initializing internal variables.
     */
    public RandomizedQueue() {
        arr = (Item[]) new Object[INIT_ARR_SIZE];
        size = 0;
    }
       
    /**
     * Is the {@code RandomizedQueue} empty?
     * 
     * @return {@code true} if the {@code deque} is empty.
     *         {@code false} otehrwise.
     */
    public boolean isEmpty() {
        return (size == 0);
    }
    
    /**
     * Return the number of items on the {@code RandomizedQueue}.
     * 
     * @return The number of items on the {@code RandomizedQueue}.
     */
    public int size() {
        return size;
    }
   
    /**
     * Resize the underlying array of the holding the elements.
     * 
     * @param The new array size being asked.
     */
    private void resize(int capacity) {
        assert capacity >= size;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }
    
    /**
     * Add the item to the {@code RandomizedQueue}. New items are added at the end.
     * 
     * @param item  The item to add.
     * 
     * @throws IllegalArgumentException if {@code item} is {@code null}.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item can't be added");
        }
        if (size == arr.length) {
            resize(arr.length * 2);
        }
        arr[size++] = item;
    }
    
    /**
     * Remove and return a random item from the {@code RandomizedQeueue}.
     * 
     * @return The item chosen at random.
     * 
     * @throws NoSuchElementException If trying to remove an item from an empty
     *                                {@code RandomizedQueue}.
     */
    public Item dequeue() {
        Item item;
        int index;
        
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove an item from empty RandomizedQueue.");
        }
        
        // Select an index at random
        index = StdRandom.uniform(size);
        
        // Pick the element.
        item = arr[index];
        
        // Move the last item to this index
        arr[index] = arr[size - 1];
        
        // Clear the last item.
        arr[size - 1] = null;
        size--;     
        
        // Shrink if needed.
        if (size > 0 && size == arr.length/4) {
            resize(arr.length/2);
        }
        
        return item;
    }
    
    /**
     * Return (but do not remove) a random item from the {@code RandomizedQeueue}.
     * 
     * @return The item chosen at random.
     * 
     * @throws NoSuchElementException If trying to remove an item from an empty
     *                                {@code Randomized Queue}.
     */
    public Item sample() {
        Item item;
        int index;
        
        if (isEmpty()) {
            throw new NoSuchElementException("Can't sample an item from empty RandomizedQueue.");
        }
        
        // Select an index at random
        index = StdRandom.uniform(size);
        
        // Pick the element.
        item = arr[index];
        
        return item;
    }
    
    /**
     * Return an iterator that goes over items in the {@code RandomizedQueue} in 
     * a random fashion.
     * 
     * @return Iterator for this {@code RandomizedQueue}.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    /**
     * The {@RandomizedQueueIterator} class implements the {@code hasNext()} and 
     * {@code next} methods from the {@code Iteraror} interface for the
     * {@RandomizedQueue} class.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] shuffleArr = new int[size];
        private int index;
        private boolean shuffled = false;
        
        
        private void shuffleArray() {
            if (shuffled) {
                return;
            }
            
            // This array contains indexes corresponding to the items in the arr.
            for (int i = 0; i < size; i++) {
                shuffleArr[i] = i;
            }
        
            // Randmize the indexes.
            StdRandom.shuffle(shuffleArr);
        }
        
        
        public boolean hasNext() {
            return index < size;
        }
        
        public Item next() {         
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return");
            }
            shuffleArray();
            
            return arr[index++];
        }
        
        public void remove() {
            throw new UnsupportedOperationException("Remove in the iterator is not supported");
        }
    }
    
    /**
     * Some simple unit testing. (Lack of time always equals to skipping unit testing).
     * 
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        
        randomizedQueue.enqueue("fourth");
        randomizedQueue.enqueue("third");
        StdOut.printf("Size of deque = %d. Is it empty? %s\n",
                      randomizedQueue.size(), randomizedQueue.isEmpty() ? "Yes" : "No");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
        
        randomizedQueue.enqueue("second");
        StdOut.printf("Size of deque = %d. Is it empty? %s\n",
                      randomizedQueue.size(), randomizedQueue.isEmpty() ? "Yes" : "No");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }

        randomizedQueue.dequeue();
        StdOut.printf("Size of deque = %d.  Is it empty? %s\n",
                      randomizedQueue.size(), randomizedQueue.isEmpty() ? "Yes" : "No");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
        
        randomizedQueue.dequeue();
        StdOut.printf("Size of deque = %d.  Is it empty? %s\n",
                      randomizedQueue.size(), randomizedQueue.isEmpty() ? "Yes" : "No");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
        

        randomizedQueue.dequeue();
        StdOut.printf("Size of deque = %d.  Is it empty? %s\n",
                      randomizedQueue.size(), randomizedQueue.isEmpty() ? "Yes" : "No");
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
        
        randomizedQueue.dequeue(); // Will throw an exception. */
    }
}