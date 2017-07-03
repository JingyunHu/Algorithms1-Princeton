/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    
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
    private boolean topRowOpen, bottomRowOpen;
    private int topRowSite, bottomRowSite, numOpenSites, rowColSize;
    private WeightedQuickUnionUF weightedQuickFind;
    
    /**
     * Initialize the fields in the {@code Percolation} class including creating
     * a weighted quick union-find data type to remember the connected Open sites.
     * 
     * @param n The # of rows and columns in the grid.
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Must enter a value greater than "
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
        topRowOpen = bottomRowOpen = false;
        topRowSite = (rowColSize - 1) * (rowColSize - 1) + 1;
        bottomRowSite = topRowSite + 1;
        for (int i = 0; i < rowColSize; i++) {
            weightedQuickFind.union(i, topRowSite); // Connect the last but one to all top sites.
            weightedQuickFind.union(rowColSize - i, bottomRowSite); // Connect the last one to all bottom sites.
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
     */
    public void open(int row, int col) {
        int currentSite, leftNeighbor, rightNeighbor, upNeighbor, downNeighbor;
        
        currentSite = getCurrentSite(row, col);
        if (site[row][col]) {
            return;
        }
        numOpenSites++;
            
        // Mark this site Open.
        site[row][col] = true;
        // Connect to the neighbor on the left.
        if (col > 0) {
            leftNeighbor = (rowColSize - 1) * row + (col - 1);
            if (site[row][col - 1]) {
                weightedQuickFind.union(currentSite, leftNeighbor);
            }
        }
        // Connect to the neighbor on the right.
        if (col < rowColSize - 1) {
            rightNeighbor = (rowColSize - 1) * row + (col + 1);
            if (site[row][col + 1]) {
                weightedQuickFind.union(currentSite, rightNeighbor);
            }
        }
        // Connect to the neighbor above.
        if (row > 0) {
            upNeighbor = (rowColSize - 1) * (row - 1) + col;
            if (site[row - 1][col]) {
                weightedQuickFind.union(currentSite, upNeighbor);
            }
        }
        // Connect to the neighbor below.
        if (row < rowColSize - 1) {
            downNeighbor = (rowColSize - 1) * row + col;
            if (site[row + 1][col]) {
                weightedQuickFind.union(currentSite, downNeighbor);
            }
        }
        
        // Open the top or bottom row if it wasn't open already if a site in the
        // first site in top or bottom row just opened up.
        if (row == 0) {
            if (!topRowOpen) {
                topRowOpen = true;
                for (int i = 0; i < rowColSize; i++) {
                    weightedQuickFind.union(i, topRowSite); // Connect the last but one to all top sites.
                }
            }
        }
        if (row == rowColSize) {
            if (!bottomRowOpen) {
                bottomRowOpen = true;
                for (int i = 0; i < rowColSize; i++) {
                    weightedQuickFind.union(i, bottomRowSite); // Connect the last one to all bottom sites.
                }
            }
        }           
    }
    
    /**
     * Check if a particular site is open.
     * 
     * @param row The this site is located in.
     * @param col The column this site is located at.
     * 
     * @return {@code True} if the site is open, {@code false} otherwise.
     */
    public boolean isOpen(int row, int col) {
        int currentSite;
        
        currentSite = getCurrentSite(row, col);

        return site[row][col];
    }
    
   /**
     * Check if a particular site is open and reachable from any of the sites
     * from the top.
     * 
     * @param row The this site is located in.
     * @param col The column this site is located at.
     * 
     * @return {@code True} if the site is open and connected to one of the top
     *         sites, {@code false} otherwise.
     */
    public boolean isFull(int row, int col) {
        int currentSite;
        
        currentSite = getCurrentSite(row, col);
        return weightedQuickFind.connected(currentSite, topRowSite);
    }
    
    /**
     * The # of open sites in the grid.
     * 
     * @return The # of open sites in the grid.
     */
    public int numberofOpenSites() {
        return numOpenSites;
    }
    
    /**
     * Check if any site on the top row is connected to any site on the bottom row.
     * 
     * @return {@code True} if any site on the top site is connected to any site
     *         at the bottomr, {@code false} otherwise.
     */
    public boolean percolates() {
        return weightedQuickFind.connected(topRowSite, bottomRowSite);
    }
    /* public static void main(String[] args) {
    }*/
}