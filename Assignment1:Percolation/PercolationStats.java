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
    private int totSites;
    
    private double findPercolationThreshold (int n, int trial_num) {
        Percolation percolation;
        int row, col;
        double percolationThreshold;
        
        percolation = new Percolation(n);
        totSites = n * n;
        while (!percolation.percolates()) {
            row = StdRandom.uniform(1, 1 + n);
            col = StdRandom.uniform(1, 1 + n);
            //System.out.println("row = " + row + "col = " + col);
            percolation.open(row, col);
        }
        percolationThreshold = (double)percolation.numberofOpenSites()/ totSites;
        
        return percolationThreshold;
    }
    
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        trialResults = new double[trials];
        
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Must enter a value greater than "
                                               + "0. You entered n = " 
                                               + Integer.toString(n) + " and trails = "
                                               + Integer.toString(trials) + ".");
        }
        StdRandom.setSeed(SEED);
        for (int i = 0; i < trialResults.length; i++) {
            trialResults[i] = findPercolationThreshold(n, i);
            // System.out.println("Trial " + i + " = " + trialResults[i]);
        }
        
        
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(trialResults);
    }
    public double stddev() {                       // sample standard deviation of percolation threshold
        return StdStats.stddev(trialResults);
    }
    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        double percolationMean, percolationStdDev, lowEndpoint;
        
        percolationMean = mean();
        percolationStdDev = stddev();
        lowEndpoint = percolationMean - (1.96 * percolationStdDev) / Math.sqrt(totSites);
        
        return lowEndpoint;
    }
    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        double percolationMean, percolationStdDev, hiEndpoint;
        
        percolationMean = mean();
        percolationStdDev = stddev();
        hiEndpoint = percolationMean + (1.96 * percolationStdDev) / Math.sqrt(totSites);
        
        return hiEndpoint;
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(202, 10);
        
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo()
                           + ", " + percolationStats.confidenceHi() + "]");
    }
}