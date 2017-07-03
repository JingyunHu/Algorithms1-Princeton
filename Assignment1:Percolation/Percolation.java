/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java Percolation
 *
 *  Percolation data type.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The {@code Percolation} class represents a <em>data type</em> for modelling
 * percolation system as defined at <a href="http://coursera.cs.princeton.edu/algs4/assignments/percolation.html"> Programming Assignment 1: Percolation </a>
 * <p>
 * {@code Percolation} class is implemented using {@code WeightedQuickUnionUF}
 * class. Each site in the {@code n * n} grid is represented on the  {@code WeightedQuickUnionUF}
 * by an index. Two additional elements are added to the array to represent 
 * <ul>
 * <li><em>Top row site </em>- Site connected to all top row sites.
 * <li><em>Bottom row site </em> - Site connected to all bottom row sites.
 * </ul>
 * This enables us check if the system is percolation by just checking if the 
 * <em>Top row site</em> is connected to the <em>Bottom row site </em>.
 * 
 * @author Ashutosh Grewal
 */
public class Percolation {
    private boolean[][] site;
    private int topRowSite, bottomRowSite, numOpenSites, rowColSize;
    private WeightedQuickUnionUF weightedQuickFind;
    
    /**
     * Create a weighted quick union-find data type to remember the connected
     * Open Sites.
     * 
     * @param n The # of rows and columns in the grid.
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Must enter a value greater than"
                                               + "0. You entered " 
                                               + Integer.toString(n) + ".");
        }
        weightedQuickFind = new WeightedQuickUnionUF(n * n + 2);
        site = new boolean[n][n];
        rowColSize = n;
        // Mark all sites as not connected
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                site[i][j] = false;
            }
        }
        numOpenSites = 0;       
        topRowSite = (rowColSize - 1) * (rowColSize -1 ) + 1;
        bottomRowSite = topRowSite + 1;
        for (int i = 0; i < n; i++) {
            weightedQuickFind.union(i, topRowSite); // Connect the last but one to all top sites.
            weightedQuickFind.union(n - i, bottomRowSite); // Connect the last one to all bottom sites.
        }
    }
    
    /**
     * Return the index in the weighted quick union-find data type corresponding
     * to this site.
     * 
     * @param row The row this site is located in.
     * @param col The colum this site is located at.
     * 
     * @return Index in the weighted quick-find union array.
     */
    private int getCurrentSite(int row, int col) {
        return ((rowColSize - 1) * row + col);
    }
        
    /**
     * Mark a particular site open in the grid. When the site is opened, it is
     * connected to all the open neighboring sites to the left, right, upward or
     * downward direction.
     * <p>
     * We also keep track of the # of open sites in this method.
     * 
     * @param row The row this site is located in.
     * @param col The column this site is located at.
     * @throws IndexOutOfBoundsException if {(i <= 0 || i > n) throw new
     */
    public void open(int row, int col) {
        int currentSite, leftNeighbor, rightNeighbor, upNeighbor, downNeighbor;
        
        currentSite = getCurrentSite(row, col);
        if (site[row][col] == true) {
            return;
        }
        numOpenSites++;
            
        // Mark this site Open.
        site[row][col] = true;
        //Connect to the neighbor on the left.
        if (col > 0) {
            leftNeighbor = (rowColSize - 1) * row + (col - 1);
            if (site[row][col - 1] == true) {
                weightedQuickFind.union(currentSite, leftNeighbor);
            }
        }
        //Connect to the neighbor on the right.
        if (col < rowColSize - 1) {
            rightNeighbor = (rowColSize - 1) * row + (col + 1);
            if (site[row][col + 1] == true) {
                weightedQuickFind.union(currentSite, rightNeighbor);
            }
        }
        //Connect to the neighbor above.
        if (row > 0) {
            upNeighbor = (rowColSize - 1) * (row - 1) + col;
            if (site[row - 1][col] == true) {
                weightedQuickFind.union(currentSite, upNeighbor);
            }
        }
        //Connect to the neighbor below.
        if (row < rowColSize - 1) {
            downNeighbor = (rowColSize - 1) * row + col;
            if (site[row + 1][col] == true) {
                weightedQuickFind.union(currentSite, downNeighbor);
            }
        }
    }
    
    public boolean isOpen(int row, int col) {
        int currentSite;
        
        currentSite = getCurrentSite(row, col);

        return site[row][col];
    }
    public boolean isFull(int row, int col) {
        int currentSite;
        
        currentSite = getCurrentSite(row, col);
        return weightedQuickFind.connected(currentSite, topRowSite);
    }
    public int numberofOpenSites() {
        return numOpenSites;
    }
    public boolean percolates() {
        return weightedQuickFind.connected(topRowSite, bottomRowSite);
    }
    /* public static void main(String[] args) {
    }*/
}