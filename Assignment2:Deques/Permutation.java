/******************************************************************************
 *  Compilation:  javac-algs4 Permutation.java 
 *  Execution:    java-algs4 Permutation.java 
 *
 *  Permutation client program.
 * 
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Permutation} class is a client program that takes a command-line
 * integer {@code k}; reads in a sequeuence of strings from standard input and
 * prints exactly {@code k} of them, uniformly at random .
 * More at <a href="http://coursera.cs.princeton.edu/algs4/assignments/queues.html">Programming Assignment 2: Deques and Randomized Queues</a>
 * 
 * @author Ashutosh Grewal
 */
public class Permutation {
    /**
     * The main function that does the work.
     * 
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        int k, index = 0;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        
        // Read #k.
        k = Integer.parseInt(args[0]);
        
        // Read n strings.
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        
        // Print k at random.
        for (String s : randomizedQueue) {
            if (index >= k) {
                return;
            }
            index++;
            StdOut.println(s);
        }
    }
}