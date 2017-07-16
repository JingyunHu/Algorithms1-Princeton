/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    No main method. I only verified via unit testing.
 *
 *  Percolation data type.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * The {@code Percolation} class represents a <em>data type</em> for modelling
 * percolation system as defined at <a href="http://coursera.cs.princeton.edu/algs4/assignments/percolation.html">Programming Assignment 1: Percolation</a>
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
 * 
 * @see <a href="http://coursera.cs.princeton.edu/algs4/checklists/percolation.html">Checklist: Percolation</a>
 *      This implementation suffers from the backwash problem described in the 
 *      FAQ section of the checklist.
 */
public class Percolation {
    private boolean[][] site;
    private boolean topRowOpen, bottomRowOpen;
    private final int topRowSite, bottomRowSite, rowColSize;
    private int numOpenSites;
    private final WeightedQuickUnionUF weightedQuickFind;
    
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
        for (int i = 0; i < rowColSize; i++) {
            for (int j = 0; j < rowColSize; j++) {
                site[i][j] = false;
            }
        }
        numOpenSites = 0;
        topRowOpen = false;
        bottomRowOpen = false;
        topRowSite = (rowColSize * rowColSize);
        bottomRowSite = topRowSite + 1;
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
    private int getSite(int row, int col) {
        return ((rowColSize * row) + col);
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
     * @throws IndexOutOfBoundsException if {@code (row <= 0 || row > rowColSize)
     *         or (col <= 0 || col > rowColSize)}.
     */
    public void open(int row, int col) {
        int currentSite, rowIndex, colIndex, leftNeighbor, rightNeighbor, upNeighbor, downNeighbor;
        
        if ((row <= 0 || row > rowColSize) || (col <= 0 || col > rowColSize)) { 
            throw new IllegalArgumentException("row/column (" + row + "/" + col
                                                + ") out of bounds " + "(1 - "
                                                + rowColSize + ").");
        }
        rowIndex = row - 1;
        colIndex = col - 1;
        currentSite = getSite(rowIndex, colIndex);

        if (site[rowIndex][colIndex]) {
            return;
        }
        numOpenSites++;
            
        // Mark this site Open.
        site[rowIndex][colIndex] = true;
        // Connect to the neighbor on the left.
        if (colIndex > 0) {
            leftNeighbor = getSite(rowIndex, colIndex - 1);
            if (site[rowIndex][colIndex - 1]) {
                // System.out.println(currentSite + " - left " + leftNeighbor);
                weightedQuickFind.union(currentSite, leftNeighbor);
            }
        }
        // Connect to the neighbor on the right.
        if (colIndex < rowColSize - 1) {
            rightNeighbor = getSite(rowIndex, colIndex + 1);
            if (site[rowIndex][colIndex + 1]) {
                // System.out.println(currentSite + " - right " + rightNeighbor);
                weightedQuickFind.union(currentSite, rightNeighbor);
            }
        }
        // Connect to the neighbor above.
        if (rowIndex > 0) {
            upNeighbor = getSite(rowIndex - 1, colIndex);
            if (site[rowIndex - 1][colIndex]) {
                // System.out.println(currentSite + " - up" + upNeighbor);
                weightedQuickFind.union(currentSite, upNeighbor);
            }
        }
        // Connect to the neighbor below.
        if (rowIndex < rowColSize - 1) {
            downNeighbor = getSite(rowIndex + 1, colIndex);
            if (site[rowIndex + 1][colIndex]) {
                // System.out.println(currentSite + " - down" + downNeighbor);
                weightedQuickFind.union(currentSite, downNeighbor);
            }
        }
        
        // Open the top or bottom row if it wasn't open already if a site in the
        // first site in top or bottom row just opened up.
        if (rowIndex == 0) {
            if (!topRowOpen) {
                topRowOpen = true;
                for (int i = 0; i < rowColSize; i++) {
                    // Connect the last but one to all top sites.
                    weightedQuickFind.union(getSite(0, i), topRowSite); 
                }
            }
        }
        if (rowIndex == (rowColSize - 1)) {
            if (!bottomRowOpen) {
                bottomRowOpen = true;
                for (int i = 0; i < rowColSize; i++) {
                    // Connect the last one to all bottom sites.
                    weightedQuickFind.union(getSite(rowColSize - 1, i), bottomRowSite);
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
     * @throws IndexOutOfBoundsException if {@code (row <= 0 || row > rowColSize)
     *         or (col <= 0 || col > rowColSize)}.
     */
    public boolean isOpen(int row, int col) {
        if ((row <= 0 || row > rowColSize) || (col <= 0 || col > rowColSize)) { 
            throw new IllegalArgumentException("row/column (" + row + "/" + col
                                                + ") out of bounds " + "(1 - "
                                                + rowColSize + ").");
        }
        return site[row - 1][col - 1];
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
     * @throws IndexOutOfBoundsException if {@code row <= 0 || row > rowColSize)
     *         or (col <= 0 || col > rowColSize)}.
     */
    public boolean isFull(int row, int col) {
        int currentSite;
        
        if ((row <= 0 || row > rowColSize) || (col <= 0 || col > rowColSize)) { 
            throw new IllegalArgumentException("row/column (" + row + "/" + col
                                                + ") out of bounds " + "(1 - "
                                                + rowColSize + ").");
        }
        // If the site is not Open, then it is not Full.
        if (!site[row - 1][col - 1]) {
            return false;
        }  
        currentSite = getSite(row - 1, col - 1);
        
        return weightedQuickFind.connected(currentSite, topRowSite);
    }
    
    /**
     * The # of open sites in the grid.
     * 
     * @return The # of open sites in the grid.
     */
    public int numberOfOpenSites() {
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
}