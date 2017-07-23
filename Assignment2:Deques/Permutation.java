import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


    public class Permutation {
    
    public static void main (String[] args) {
        int k;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        
        // Read #k.
        k = Integer.parseInt(args[0]);
        
        // Read n strings.
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        
        // Print k at random.
        for (String s : randomizedQueue) {
            StdOut.println(s);
        }
    }
}