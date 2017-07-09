/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *                javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats 20 10
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
    // private static final int SEED = 700;
    private double[] trialResults;
    private int totSites;
         
    /**
     * Find the percolation threshold {@code trials} # of times on an n-by-n grid.
     * 
     * @param n The size of the symmetric grid's single dimension.
     * @param trials The # of times computation of threshold is requested.
     * 
     * @throws IllegalArgumentException if either {@code n ≤ 0} or {@code trials ≤ 0}.
     */
    public PercolationStats(int n, int trials) {
        trialResults = new double[trials];
        
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Must enter a value greater than "
                                               + "0. You entered n = " 
                                               + Integer.toString(n) + " and trails = "
                                               + Integer.toString(trials) + ".");
        }
        // StdRandom.setSeed(SEED);
        for (int i = 0; i < trialResults.length; i++) {
            trialResults[i] = findPercolationThreshold(n);
            // System.out.println("Trial " + i + " = " + trialResults[i]);
        }
        
        
    }
    
    /**
     * Find the percolation threshold for a grid of size {@code n}-by-{@code n}.
     * 
     * @param n The size of dimension of the symmetric grid.
     * 
     * @return The percolation threshold for this run.
     */
    private double findPercolationThreshold(int n) {
        Percolation percolation;
        int row, col;
        double percolationThreshold;
        
        percolation = new Percolation(n);
        totSites = n * n;
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, 1 + n);
            col = StdRandom.uniform(1, 1 + n);
            // System.out.println("row = " + row + "col = " + col);
            percolation.open(row, col);
        }
        percolationThreshold = (double) percolation.numberOfOpenSites() / totSites;
        
        return percolationThreshold;
    }
    
    /**
     * Compute the sample mean of the percolation threshold.
     * 
     * @return The mean of all the percolation computations for this grid.
     */
    public double mean() {
        return StdStats.mean(trialResults);
    }
    
    /**
     * Compute the standard deviation of the percolation threshold.
     * 
     * @return The standard deviation of all the percolation computations for this grid.
     */
    public double stddev() {
        return StdStats.stddev(trialResults);
    }
    
    /**
     * Compute the lower endpoint of the 95% confidence interval.
     * 
     * @return Low endpoint of the 95% confidence interval.
     */
    public double confidenceLo() {
        double percolationMean, percolationStdDev, lowEndpoint;
        
        percolationMean = mean();
        percolationStdDev = stddev();
        lowEndpoint = percolationMean - (1.96 * percolationStdDev) / Math.sqrt(totSites);
        
        return lowEndpoint;
    }
    
    /**
     * Compute the high endpoint of the 95% confidence interval.
     * 
     * @return High endpoint of the 95% confidence interval.
     */
    public double confidenceHi() {
        double percolationMean, percolationStdDev, hiEndpoint;
        
        percolationMean = mean();
        percolationStdDev = stddev();
        hiEndpoint = percolationMean + (1.96 * percolationStdDev) / Math.sqrt(totSites);
        
        return hiEndpoint;
    }

    public static void main(String[] args) {
        int n, trials;
        PercolationStats percolationStats;
        
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } else {
            throw new IllegalArgumentException("You must run this program with two command-line " +
                                               "arguments - n and trials. The first argument (n) controls " +
                                               "the size of the n-by-n grid while trials represent " +
                                               "the # of independent computational experiments " +
                                               "on this grid to find the percolation threshold.");
        }
        percolationStats = new PercolationStats(n, trials);
        
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo()
                           + ", " + percolationStats.confidenceHi() + "]");
    }
}