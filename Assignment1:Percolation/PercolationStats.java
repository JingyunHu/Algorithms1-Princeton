/******************************************************************************
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    No main method. I only verified via unit testing.
 *
 *  PercolationStats data type.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * The {@code PercolationStats} class represents a <em>data type</em> for to
 * perform a series of computational experiments as defined at 
 * <a href="http://coursera.cs.princeton.edu/algs4/assignments/percolation.html"> Programming Assignment 1: Percolation </a>.
 * 
 * @author Ashutosh Grewal
 */
public class PercolationStats {
    private double[] trialResults;
    private static final int SEED = 700;
    
    private double findPercolationThreshold (int n, int trial_num) {
        Percolation percolation;
        int row, col;
        double percolationThreshold;
        
        percolation = new Percolation(n);
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, 1 + n);
            col = StdRandom.uniform(1, 1 + n);
            
            percolation.open(row, col);
        }
        percolationThreshold = (double)percolation.numberofOpenSites()/n;
        
        return percolationThreshold;
    }
    
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        trialResults = new double[trials];
        
        StdRandom.setSeed(SEED);
        for (int i = 0; i < trialResults.length; i++) {
            trialResults[i] = findPercolationThreshold(n, i);
            // System.out.println("Trial " + i + " = " + trialResults[i]);
        }
        
        
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(trialResults);
    }
   public double stddev()                        // sample standard deviation of percolation threshold
   /*public double confidenceLo()                  // low  endpoint of 95% confidence interval
   public double confidenceHi()                  // high endpoint of 95% confidence interval */

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(2, 10);
        
        System.out.println("mean                    = " + percolationStats.mean());
    }
}